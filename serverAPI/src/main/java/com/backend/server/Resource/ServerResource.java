
package com.backend.server.Resource;

import com.backend.server.Service.ServerService;
import com.backend.server.enumeration.Status;
import com.backend.server.model.Response;
import com.backend.server.model.Server;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.time.LocalDateTime.now;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {

	private final ServerService serverService;

	@GetMapping("/servers")
	public ResponseEntity<Response> getServers() throws InterruptedException {
		TimeUnit.SECONDS.sleep(0);  //put the LOADING STATE
		//throw new RuntimeException("Something went wrong");
		return ResponseEntity.ok(Response.builder().dateTime(now()).data(Map.of("servers", serverService.list(30)))
				.message("servers retrieved").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
		Server server = serverService.ping(ipAddress);
		return ResponseEntity.ok(Response.builder().dateTime(now()).data(Map.of("server", server))
				.message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed").status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value()).build());// 200
	}

	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {

		return ResponseEntity.ok(Response.builder().dateTime(now()).data(Map.of("server", serverService.create(server)))
				.message("server created").status(HttpStatus.CREATED).statusCode(HttpStatus.CREATED.value()).build());// 201
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.builder().dateTime(now()).data(Map.of("server", serverService.get(id)))
				.message("Server retrieved").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());// 200
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.builder().dateTime(now()).data(Map.of("deleted", serverService.delete(id)))
				.message("Server deleted").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());// 200
	}
	
	@GetMapping(path = "/image/{imageName}", produces = IMAGE_PNG_VALUE)
	public byte[] getServerImage(@PathVariable("imageName") String imageName) throws IOException {
		return Files.readAllBytes(Paths.get("D:\\programacion\\java\\spring-boot\\server\\src\\main\\java\\com\\backend\\server\\images\\"+ imageName));
	}

}
