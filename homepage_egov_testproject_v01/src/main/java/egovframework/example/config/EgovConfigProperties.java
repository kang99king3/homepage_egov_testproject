package egovframework.example.config;

import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EgovConfigProperties {

	@Value("${Globals.fileStorePath}")
	private String fileStorePath;

	@Value("${Globals.addedOptions}")
	private String addedOptions;

	@Value("${Globals.pageUnit}")
	private String pageUnit;
	@Value("${Globals.pageSize}")
	private String pageSize;
	@Value("${Globals.posblAtchFileSize}")
	private String posblAtchFileSize;
	
	@Bean(destroyMethod="destroy")
	public EgovPropertyServiceImpl propertiesService() {
		Map<String, String> properties = new HashMap<>();
		properties.put("pageUnit", "10");
		properties.put("pageSize", "10");
		properties.put("Globals.posblAtchFileSize", posblAtchFileSize);
		properties.put("Globals.fileStorePath", fileStorePath);
		properties.put("Globals.addedOptions", addedOptions);
		
		EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();
		egovPropertyServiceImpl.setProperties(properties);
		
		return egovPropertyServiceImpl;
	}

}
