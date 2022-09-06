package com.backend.server.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.backend.server.enumeration.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Server {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    @NotEmpty(message = "ip address cannot be null")
    private String ipAddress; 
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;
}
