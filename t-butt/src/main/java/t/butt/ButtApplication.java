package t.butt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("t.butt.dao")
public class ButtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ButtApplication.class, args);
    }

}
