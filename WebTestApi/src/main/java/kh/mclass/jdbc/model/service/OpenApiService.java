package kh.mclass.jdbc.model.service;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import kh.mclass.jdbc.common.JdbcTemplate;
import kh.mclass.jdbc.model.vo.OpenApiVo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class OpenApiService {
	private Properties prop = new Properties();

	public void getMinuDustFrcstDspth() {
		// TODO
	}

	public List<OpenApiVo> getCtprvnRltmMesureDnsty() throws IOException { // ArpltnInforInqireSvc
		List<OpenApiVo> volist = null;

		prop.load(new FileReader(JdbcTemplate.class.getResource("./").getPath() + "driver.properties"));
		System.out.println(JdbcTemplate.class.getResource("./").getPath() + "driver.properties");

		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="
				+ URLEncoder.encode(prop.getProperty("openapi.servicekey.ArpltnInforInqireSvc")));

		urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "="
				+ URLEncoder.encode("xml", "UTF-8")); /* xml 또는 json */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("100", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode("서울",
				"UTF-8")); /*
							 * 시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
							 */
		urlBuilder.append(
				"&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /* 버전별 상세 결과 참고 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());

		/*
		 * BufferedReader rd; if (conn.getResponseCode() >= 200 &&
		 * conn.getResponseCode() <= 300) { rd = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream())); } else { rd = new
		 * BufferedReader(new InputStreamReader(conn.getErrorStream())); }
		 */
		/*
		 * StringBuilder sb = new StringBuilder(); String line; while ((line =
		 * rd.readLine()) != null) { sb.append(line); } rd.close(); conn.disconnect();
		 * System.out.println(sb.toString());
		 */

		// 읽은 데이터 활용
		// 방법 2 - a - conn --> InputStream --> Xml --> Document( HTMLXML) --> item 들 -->
		// node들을 하나씩 읽기
		// parseXML(conn.getInputStream())를 사용해 Document doc 생성
		Document doc = parseXML(conn.getInputStream());
		conn.disconnect();
		// item 들을 NodeList nodelist 담기
		NodeList nodelist = doc.getElementsByTagName("item"); // Elements s가 붙어있음

		// item 데이터가 없다면 바로 null return;
		if (nodelist.getLength() < 1) {
			return volist;
		}

		// item 데이터가 있다면
		volist = new ArrayList<OpenApiVo>();
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node item = nodelist.item(i);
			Node n = item.getFirstChild(); // 첫번쩨 노드 가져옴
			OpenApiVo vo = new OpenApiVo();

			while (n != null) {
				String nodeName = n.getNodeName();
				String nodeText = n.getTextContent(); // 태그와 태그 사이에 있는것을 꺼냄
				System.out.println(nodeName + " : " + nodeText);

				switch (nodeName) {
				case "stationName":
					vo.setStationName(nodeText);
					break;
				case "dataTime":
					vo.setDataTime(nodeText);
					break;
				case "pm10Value":
					vo.setPm10Value(nodeText);
					break;
				case "so2Value":
					vo.setSo2Value(nodeText);
					break;
				case "so2Grade":
					vo.setSo2Grade(nodeText);
					break;
				}

				n = n.getNextSibling();
			}

			volist.add(vo);
		}

		return volist;
	}

	public Map<String, Object> getCtprvnRltmMesureDnstyJson() throws IOException { // ArpltnInforInqireSvc
		prop.load(new FileReader(JdbcTemplate.class.getResource("./").getPath() + "driver.properties"));
		System.out.println(JdbcTemplate.class.getResource("./").getPath() + "driver.properties");

		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="
				+ URLEncoder.encode(prop.getProperty("openapi.servicekey.ArpltnInforInqireSvc")));

		urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* xml 또는 json */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("100", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode("서울",
				"UTF-8")); /*
							 * 시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
							 */
		urlBuilder.append(
				"&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /* 버전별 상세 결과 참고 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());  //Response code: 200

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
		System.out.println(sb.toString()); //{"response":{"body":{"totalCount":40,"items":[{"so2Grade":"1","coFlag":null,"khaiValue":"45","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"22","khaiGrade":"1","pm25Value":"8","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.026","stationName":"정릉로","pm10Grade":"1","o3Value":"0.027"},{"so2Grade":null,"coFlag":"통신장애","khaiValue":"-","so2Value":"-","coValue":"-","pm25Flag":"통신장애","pm10Flag":"통신장애","o3Grade":null,"pm10Value":"-","khaiGrade":null,"pm25Value":"-","sidoName":"서울","no2Flag":"통신장애","no2Grade":null,"o3Flag":"통신장애","pm25Grade":null,"so2Flag":"통신장애","dataTime":"2024-03-06 12:00","coGrade":null,"no2Value":"-","stationName":"도봉구","pm10Grade":"1","o3Value":"-"},{"so2Grade":"1","coFlag":null,"khaiValue":"55","so2Value":"0.003","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"13","khaiGrade":"2","pm25Value":"10","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.014","stationName":"은평구","pm10Grade":"1","o3Value":"0.036"},{"so2Grade":"1","coFlag":null,"khaiValue":"51","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"16","khaiGrade":"2","pm25Value":"9","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.019","stationName":"서대문구","pm10Grade":"1","o3Value":"0.031"},{"so2Grade":"1","coFlag":null,"khaiValue":"58","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"16","khaiGrade":"2","pm25Value":"5","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.018","stationName":"마포구","pm10Grade":"1","o3Value":"0.040"},{"so2Grade":"1","coFlag":null,"khaiValue":"50","so2Value":"0.002","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"14","khaiGrade":"1","pm25Value":"5","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.017","stationName":"신촌로","pm10Grade":"1","o3Value":"0.030"},{"so2Grade":"1","coFlag":null,"khaiValue":"50","so2Value":"0.002","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"12","khaiGrade":"1","pm25Value":"3","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.023","stationName":"강서구","pm10Grade":"1","o3Value":"0.030"},{"so2Grade":"1","coFlag":null,"khaiValue":"45","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"22","khaiGrade":"1","pm25Value":"7","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.023","stationName":"공항대로","pm10Grade":"1","o3Value":"0.027"},{"so2Grade":"1","coFlag":null,"khaiValue":"48","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"15","khaiGrade":"1","pm25Value":"4","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.012","stationName":"구로구","pm10Grade":"1","o3Value":"0.029"},{"so2Grade":"1","coFlag":null,"khaiValue":"52","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"13","khaiGrade":"2","pm25Value":"10","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.027","stationName":"영등포구","pm10Grade":"1","o3Value":"0.032"},{"so2Grade":"1","coFlag":null,"khaiValue":"45","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"19","khaiGrade":"1","pm25Value":"6","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.027","stationName":"영등포로","pm10Grade":"1","o3Value":"0.027"},{"so2Grade":"1","coFlag":null,"khaiValue":"55","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"14","khaiGrade":"2","pm25Value":"3","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.018","stationName":"동작구","pm10Grade":"1","o3Value":"0.036"},{"so2Grade":"1","coFlag":null,"khaiValue":"61","so2Value":"0.002","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"26","khaiGrade":"2","pm25Value":"7","sidoName":"서울","no2Flag":null,"no2Grade":"2","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.037","stationName":"동작대로 중앙차로","pm10Grade":"1","o3Value":"0.029"},{"so2Grade":"1","coFlag":null,"khaiValue":"55","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"15","khaiGrade":"2","pm25Value":"4","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.015","stationName":"관악구","pm10Grade":"1","o3Value":"0.036"},{"so2Grade":"1","coFlag":null,"khaiValue":"54","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"11","khaiGrade":"2","pm25Value":"3","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.016","stationName":"강남구","pm10Grade":"1","o3Value":"0.035"},{"so2Grade":"1","coFlag":null,"khaiValue":"51","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"23","khaiGrade":"2","pm25Value":"2","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.017","stationName":"서초구","pm10Grade":"1","o3Value":"0.031"},{"so2Grade":null,"coFlag":"통신장애","khaiValue":"-","so2Value":"-","coValue":"-","pm25Flag":null,"pm10Flag":null,"o3Grade":null,"pm10Value":"20","khaiGrade":null,"pm25Value":"6","sidoName":"서울","no2Flag":"통신장애","no2Grade":null,"o3Flag":"통신장애","pm25Grade":"1","so2Flag":"통신장애","dataTime":"2024-03-06 12:00","coGrade":null,"no2Value":"-","stationName":"도산대로","pm10Grade":"1","o3Value":"-"},{"so2Grade":"1","coFlag":null,"khaiValue":"45","so2Value":"0.002","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"30","khaiGrade":"1","pm25Value":"6","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.027","stationName":"강남대로","pm10Grade":"1","o3Value":"0.024"},{"so2Grade":"1","coFlag":null,"khaiValue":"56","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"16","khaiGrade":"2","pm25Value":"5","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.017","stationName":"송파구","pm10Grade":"1","o3Value":"0.037"},{"so2Grade":"1","coFlag":null,"khaiValue":"-","so2Value":"0.002","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"7","khaiGrade":null,"pm25Value":"1","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.012","stationName":"강동구","pm10Grade":null,"o3Value":"0.046"},{"so2Grade":null,"coFlag":"통신장애","khaiValue":"-","so2Value":"-","coValue":"-","pm25Flag":"통신장애","pm10Flag":"통신장애","o3Grade":null,"pm10Value":"-","khaiGrade":null,"pm25Value":"-","sidoName":"서울","no2Flag":"통신장애","no2Grade":null,"o3Flag":"통신장애","pm25Grade":"1","so2Flag":"통신장애","dataTime":"2024-03-06 12:00","coGrade":null,"no2Value":"-","stationName":"천호대로","pm10Grade":"1","o3Value":"-"},{"so2Grade":"1","coFlag":null,"khaiValue":"57","so2Value":"0.002","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"10","khaiGrade":"2","pm25Value":"2","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.018","stationName":"금천구","pm10Grade":"1","o3Value":"0.038"},{"so2Grade":"1","coFlag":null,"khaiValue":"59","so2Value":"0.003","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"25","khaiGrade":"2","pm25Value":"7","sidoName":"서울","no2Flag":null,"no2Grade":"2","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.036","stationName":"시흥대로","pm10Grade":"1","o3Value":"0.019"},{"so2Grade":"1","coFlag":null,"khaiValue":"55","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"14","khaiGrade":"2","pm25Value":"5","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.016","stationName":"강북구","pm10Grade":"1","o3Value":"0.036"},{"so2Grade":null,"coFlag":"통신장애","khaiValue":"-","so2Value":"-","coValue":"-","pm25Flag":null,"pm10Flag":"통신장애","o3Grade":null,"pm10Value":"-","khaiGrade":null,"pm25Value":"3","sidoName":"서울","no2Flag":"통신장애","no2Grade":null,"o3Flag":"통신장애","pm25Grade":"1","so2Flag":"통신장애","dataTime":"2024-03-06 12:00","coGrade":null,"no2Value":"-","stationName":"양천구","pm10Grade":"1","o3Value":"-"},{"so2Grade":null,"coFlag":"통신장애","khaiValue":"-","so2Value":"-","coValue":"-","pm25Flag":"통신장애","pm10Flag":"통신장애","o3Grade":null,"pm10Value":"-","khaiGrade":null,"pm25Value":"-","sidoName":"서울","no2Flag":"통신장애","no2Grade":null,"o3Flag":"통신장애","pm25Grade":"1","so2Flag":"통신장애","dataTime":"2024-03-06 12:00","coGrade":null,"no2Value":"-","stationName":"노원구","pm10Grade":"1","o3Value":"-"},{"so2Grade":"1","coFlag":null,"khaiValue":"47","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"1","pm10Value":"20","khaiGrade":"1","pm25Value":"5","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.028","stationName":"화랑로","pm10Grade":"1","o3Value":"0.027"},{"so2Grade":"1","coFlag":null,"khaiValue":"54","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"6","khaiGrade":"2","pm25Value":"2","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.017","stationName":"중구","pm10Grade":"1","o3Value":"0.035"},{"so2Grade":"1","coFlag":null,"khaiValue":"51","so2Value":"0.003","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"35","khaiGrade":"2","pm25Value":"8","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.021","stationName":"한강대로","pm10Grade":"1","o3Value":"0.031"},{"so2Grade":"1","coFlag":null,"khaiValue":"58","so2Value":"0.003","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"7","khaiGrade":"2","pm25Value":"3","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.013","stationName":"종로구","pm10Grade":"1","o3Value":"0.040"},{"so2Grade":"1","coFlag":null,"khaiValue":"53","so2Value":"0.002","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"15","khaiGrade":"2","pm25Value":"4","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.017","stationName":"청계천로","pm10Grade":"1","o3Value":"0.034"},{"so2Grade":"1","coFlag":null,"khaiValue":"53","so2Value":"0.003","coValue":"0.5","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"19","khaiGrade":"2","pm25Value":"3","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.020","stationName":"종로","pm10Grade":"1","o3Value":"0.034"},{"so2Grade":"1","coFlag":null,"khaiValue":"57","so2Value":"0.003","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"15","khaiGrade":"2","pm25Value":"5","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.020","stationName":"용산구","pm10Grade":"1","o3Value":"0.038"},{"so2Grade":"1","coFlag":null,"khaiValue":"57","so2Value":"0.002","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"7","khaiGrade":"2","pm25Value":"2","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.015","stationName":"광진구","pm10Grade":"1","o3Value":"0.038"},{"so2Grade":"1","coFlag":null,"khaiValue":"58","so2Value":"0.004","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"12","khaiGrade":"2","pm25Value":"6","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.013","stationName":"성동구","pm10Grade":"1","o3Value":"0.040"},{"so2Grade":"1","coFlag":null,"khaiValue":"52","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"15","khaiGrade":"2","pm25Value":"4","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.015","stationName":"강변북로","pm10Grade":"1","o3Value":"0.032"},{"so2Grade":"1","coFlag":null,"khaiValue":"54","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"9","khaiGrade":"2","pm25Value":"4","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.011","stationName":"중랑구","pm10Grade":"1","o3Value":"0.035"},{"so2Grade":"1","coFlag":null,"khaiValue":"57","so2Value":"0.002","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"13","khaiGrade":"2","pm25Value":"4","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.012","stationName":"동대문구","pm10Grade":"1","o3Value":"0.038"},{"so2Grade":"1","coFlag":null,"khaiValue":"57","so2Value":"0.003","coValue":"0.3","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"13","khaiGrade":"2","pm25Value":"3","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.015","stationName":"홍릉로","pm10Grade":"1","o3Value":"0.038"},{"so2Grade":"1","coFlag":null,"khaiValue":"52","so2Value":"0.003","coValue":"0.4","pm25Flag":null,"pm10Flag":null,"o3Grade":"2","pm10Value":"15","khaiGrade":"2","pm25Value":"6","sidoName":"서울","no2Flag":null,"no2Grade":"1","o3Flag":null,"pm25Grade":"1","so2Flag":null,"dataTime":"2024-03-06 12:00","coGrade":"1","no2Value":"0.013","stationName":"성북구","pm10Grade":"1","o3Value":"0.032"}],"pageNo":1,"numOfRows":100},"header":{"resultMsg":"NORMAL_CODE","resultCode":"00"}}}
		

		// System.out.println(sb.toString());
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(sb.toString(), map.getClass()); // gson을 쓰면 java 자료형으로 한번에 바뀜
//		System.out.println(map.get("response"));
//		System.out.println(map.get("item"));
//		System.out.println((List)(map.get("item")));
		System.out.println(map.get("item"));
		System.out.println(gson.toJson(map));

		return map;
	}

	// xml to java parse object
	private Document parseXML(InputStream stream) {
		DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder objDocumentBuilder = null;
		Document result = null;

		try {
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			result = objDocumentBuilder.parse(stream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) { // Simple API for XML
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
