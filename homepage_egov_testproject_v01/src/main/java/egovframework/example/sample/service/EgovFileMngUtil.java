package egovframework.example.sample.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.com.EgovStringUtil;
import egovframework.example.com.EgovWebUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("EgovFileMngUtil")
public class EgovFileMngUtil {

	 public static final int BUFF_SIZE = 2048;

     @Resource(name = "propertiesService")
     protected EgovPropertyService propertyService;

     @Resource(name = "egovFileIdGnrService")
     private EgovIdGnrService idgenService;
     
	 /**
     * 첨부파일에 대한 목록 정보를 취득한다.
     *
     * @param files
     * @return
     * @throws Exception
     */
     
    public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
	int fileKey = fileKeyParam;

	String storePathString = "";
	String atchFileIdString = "";

	if ("".equals(storePath) || storePath == null) {
		System.out.println("fileStorePath:"+propertyService.getString("Globals.fileStorePath"));
	    storePathString = propertyService.getString("Globals.fileStorePath");//path를 따로 설정하지 않으면 Globals에서 가져오기
	} else {
	    storePathString = propertyService.getString(storePath);
	}
	
	atchFileId = atchFileId.replaceAll("\\s", "");

	if ("".equals(atchFileId) || atchFileId == null) {
	    atchFileIdString = idgenService.getNextStringId();
	} else {
	    atchFileIdString = atchFileId;
	}

	File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));
	System.out.println("saveFoler.exists:"+!saveFolder.exists());
	if (!saveFolder.exists() || saveFolder.isFile()) {
	    saveFolder.mkdirs();
	}
	System.out.println("saveFolder:"+saveFolder.getPath());


	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	MultipartFile file;
	String filePath = "";
	List<FileVO> result  = new ArrayList<FileVO>();
	FileVO fvo;

	while (itr.hasNext()) {
	    Entry<String, MultipartFile> entry = itr.next();

	    file = entry.getValue();
	    String orginFileName = file.getOriginalFilename();
	    
	    //--------------------------------------
	    // 원 파일명이 null인 경우 처리
	    //--------------------------------------
	    if (orginFileName == null) {
	    	orginFileName = "";
	    }
	    ////------------------------------------

	    //--------------------------------------
	    // 원 파일명이 없는 경우 처리
	    // (첨부가 되지 않은 input file type)
	    //--------------------------------------
	    if ("".equals(orginFileName)) {
	    	continue;
	    }
	    ////------------------------------------
	    
		int index = orginFileName.lastIndexOf(".");
	    //String fileName = orginFileName.substring(0, index);
	    String fileExt = orginFileName.substring(index + 1);//파일확장자 추출
	    String newName = KeyStr + EgovStringUtil.getTimeStamp() + fileKey+"."+fileExt;//새로운 파일명 생성
	    long _size = file.getSize();

	    if (!"".equals(orginFileName)) {
	    	filePath = storePathString + File.separator + newName;
	    	System.out.println("FullPath = " + filePath);
	    	file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
	    }
	    fvo = new FileVO();
	    fvo.setFileExtsn(fileExt);
	    fvo.setFileStreCours(storePathString);
	    fvo.setFileMg(Long.toString(_size));
	    fvo.setOrignlFileNm(orginFileName);
	    fvo.setStreFileNm(newName);
	    fvo.setAtchFileId(atchFileIdString);
	    fvo.setFileSn(String.valueOf(fileKey));

	    //writeFile(file, newName, storePathString);
	    result.add(fvo);

	    fileKey++;
	}

	return result;
    }
}
