package com.myblog.statistics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;
import com.myblog.util.Utils;

public class PostgraduateEntranceExamination {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 1.读取单词集合路径 ：englistFile/folderPath
		String folderPath = Constant.PATH_RESOURCES + "/the old exam/英语二";
		// 2.加载集合
		List<String> wordLines = doParseEnglishFile(folderPath);

		Map<String, Integer> wordsCount = WordStatistics.StatisticsFreq(wordLines);
		// System.out.println(wordsCount.size());
		List<String> wordFreqList = WordStatistics.SortMap(wordsCount);
		System.out.println(wordFreqList.size());

		// 2.写入对应的text文件
		String cfg_englist_txt_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_englist_txt_result");
		String outTextFilename = Constant.PATH_RESOURCES + cfg_englist_txt_result;
		ResourceUtil.writerFile(outTextFilename, wordFreqList, false);
		System.out.println("done!outTextFilename=" + outTextFilename);

		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}
	   // 
    public static List<String> doParseEnglishFile(String englistFile) {
        //1.读目录中的txt文件
        //2.去掉每行尾带的连字符后直接拼接，另把每行不带连字符的行，加空格拼接成文本
        //3.分词成list并加入总词list
    	List<String> txtFileList = new ArrayList<String>();
        txtFileList = Utils.traverseFile(new File(englistFile), txtFileList, ".txt");
        
        List<String> wordLines  = new ArrayList<String>();
        for (String txtFilename:txtFileList) {
        	List<String> words = WordStatistics.getFileWord(txtFilename);
        	wordLines.addAll(words);
        }
        return wordLines;
    }
}