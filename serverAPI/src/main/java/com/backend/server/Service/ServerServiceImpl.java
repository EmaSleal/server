package com.backend.server.Service;

import com.backend.server.enumeration.Status;
import com.backend.server.model.Server;
import com.backend.server.repository.ServerRepository;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServerServiceImpl implements ServerService {

	private final ServerRepository SR;

	@Override
	public Server create(Server server) {
		log.info("saving a new server {}", server.getName());
		server.setImageUrl(SetImageUrl());
		return SR.save(server);
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("fetching all servers");
		return SR.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("fetching server by id: {}", id);
		return SR.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("updating server {}", server.getName());
		return SR.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("deleting server {}", id);
		SR.deleteById(id);
		return true;
	}

	@Override
	public Server ping(String ipAddress) {
		log.info("Pinging server IP: {}", ipAddress);
		Server server = SR.findByIpAddress(ipAddress);

		InetAddress address;
		try {
			address = InetAddress.getByName(ipAddress);
			server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		}
		catch (UnknownHostException ex) {
			Logger.getLogger(ServerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (IOException ex) {
			Logger.getLogger(ServerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

		SR.save(server);
		return server;
	}

	private String SetImageUrl() {
		String[] imageName = { "server1.png", "server2.png", "server3.png" };
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/server/image/" + imageName[new Random().nextInt(3)]).toUriString();
	}

}
