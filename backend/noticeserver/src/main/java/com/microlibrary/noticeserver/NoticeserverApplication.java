package com.microlibrary.noticeserver;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.microlibrary.commonservice.dto.EmployeeDto;


@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NoticeserverApplication {
	private Logger logger =org.slf4j.LoggerFactory.getLogger(NoticeserverApplication.class);
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	
	public static void main(String[] args) {
		SpringApplication.run(NoticeserverApplication.class, args);
	}
	
	@StreamListener(Sink.INPUT)
	public void consumeMessage(Message message){
		EmployeeDto Employeemodel = circuitBreakerFactory.create("getEmployee").run(
		() -> { EmployeeDto model = webClientBuilder.build()
				.get()
				.uri("http://localhost:8080/api/employees/" + message.getEmployeeId())
				.retrieve()
				.bodyToMono(EmployeeDto.class)
				.block();
		return model;
		},
		t -> { EmployeeDto model = new EmployeeDto();
				model.setFirstName("Anonymous");
				model.setLastName("Employee");
				return model;
		}
			);
		
		if(Employeemodel !=null) {
			logger.info("Consume Payload: " + Employeemodel.getFirstName() + " "
			+ Employeemodel.getLastName() + " " + message.getMessage());
		}
	}
}
