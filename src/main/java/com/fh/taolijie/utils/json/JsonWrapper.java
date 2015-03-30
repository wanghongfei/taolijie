package com.fh.taolijie.utils.json;

import com.fh.taolijie.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.json.*;
import java.io.StringReader;
import java.util.*;
import java.util.Map.Entry;

public class JsonWrapper extends Wrapper {
	public static final Logger logger = LoggerFactory.getLogger(JsonWrapper.class);

	protected String ajaxMessage;

	protected List<Map<String, String>> jsonList;
	// 缓存解析结果
	protected JsonArrayBuilder builder;


	public JsonWrapper(String jsonString) {
		this.jsonString = jsonString;
		this.jsonList = new ArrayList<>();
		this.builder = Json.createArrayBuilder();

		parseJson();
	}
	
	/**
	 * 构造JSON对象.
	 * <p> {result: 'true / false', message: 'message'}
	 * 
	 * @param result
	 * @param message
	 */
	public JsonWrapper(boolean result, String message) {
		this.result = result;

		JsonObject obj = Json.createObjectBuilder()
				.add("result", result)
				.add("message", message)
				.build();
		
		this.ajaxMessage = obj.toString();
	}

	/**
	 * 构造 {result: 'true', parm: {'p1': 'p1', 'p2': 'p2', ...} }
	 * 
	 * @param result 状态值，true or false
	 * @param eList 封装ObjectError的List对象
	 */	
	public JsonWrapper(boolean result, List<ObjectError> eList) {
		// 构造内部JSON对象
		JsonObjectBuilder innerBuilder = Json.createObjectBuilder();
		int ix = 0;
		String key = null;
		String value = null;
		// 遍历Error对象
		for (ObjectError err : eList) {
			if (err instanceof FieldError) {
				FieldError fErr = (FieldError)err;
				key = fErr.getField();
				value = fErr.getDefaultMessage();
			} else {
				key = Integer.toString(ix);
				value = err.getDefaultMessage();
			}
			
			// 把错误信息添加到JSON中
			innerBuilder.add(key, value);
			
			++ix;
		}
		
		// 构造外部JSON
		JsonObject json = Json.createObjectBuilder()
				.add("result", result)
				.add("parm", innerBuilder)
				.build();
		
		this.ajaxMessage = json.toString();
	}
	/**
	 * 构造 {result: 'true', parm: {'p1': 'p1', 'p2': 'p2', ...} }
	 * 
	 * @param result 状态值，true or false
	 */	
	public JsonWrapper(boolean result, Map<String, String> parmMap) {
		// 遍历Map
		String key = null;
		String value = null;
		Set<Entry<String, String>> entrySet = parmMap.entrySet();
		// 构造内部JSON对象
		JsonObjectBuilder innerBuilder = Json.createObjectBuilder();
		for (Entry<String, String> entry : entrySet) {
			key = entry.getKey();
			value = entry.getValue();
			
			// 添加到JSON中
			innerBuilder.add(key, value);
		}
		
		// 构造外部JSON对象
		JsonObject json = Json.createObjectBuilder()
				.add("result", result)
				.add("parm", innerBuilder)
				.build();
		
		this.ajaxMessage = json.toString();
	}
	/**
	 * 构造 {result: 'true', parm: {'p1': 'p1', 'p2': 'p2', ...} }
	 * 
	 * @param result 状态值，true or false
	 * @param args 参数个数必须为2的倍数
	 */
	public JsonWrapper(boolean result, String... args) {
		this.result = result;

		int LEN = args.length;
		if (LEN  <= 0 || LEN % 2 != 0) {
			throw new RuntimeException("从第2个参数起，参数数量必须为2的倍数");
		}
		
		// 构造内部JSON对象
		String parmName = null;
		String parmValue = null;
		JsonObjectBuilder innerObjBuilder = Json.createObjectBuilder();
		for (int ix = 0 ; ix < LEN ; ix += 2) {
			parmName = args[ix];
			parmValue = args[ix + 1];

			innerObjBuilder.add(parmName, parmValue);
		}
		
		JsonObject innerObj = innerObjBuilder.build();
		
		JsonObject outerObj = Json.createObjectBuilder()
				.add("result", result)
				.add("parm", innerObj)
				.build();
		
		this.ajaxMessage = outerObj.toString();
		
	}

	public String getAjaxMessage() {
		return ajaxMessage;
	}

	public String getAjaxMessage(boolean flush) {
		if (flush) {
			this.ajaxMessage = builder.build().toString();
		}

		return this.ajaxMessage;
	}

	public List<Map<String, String>> getJsonList() {
		return jsonList;
	}

	public void addObjectToArray(List<Map.Entry<String, String>> objList) {
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();

		for (Entry<String, String> pair : objList) {
			objBuilder.add(pair.getKey(), pair.getValue());
		}

		builder.add(objBuilder);
	}

	private void parseJson() {
		if (null == jsonString || jsonString.isEmpty()) {
			throw new IllegalStateException("json string is null or empty");
		}

		// Convert json string to JsonObject
		JsonReader reader = Json.createReader(new StringReader(jsonString));
		JsonArray arr = reader.readArray();

		for (JsonValue jsonVal : arr) {
			JsonObjectBuilder objBuilder = Json.createObjectBuilder();

			JsonValue.ValueType type = jsonVal.getValueType();
			if (type == JsonValue.ValueType.OBJECT) {
				JsonObject obj = (JsonObject) jsonVal;

				Map<String, String> objMap = new HashMap<>();
				Set<Entry<String, JsonValue>> entrySet = obj.entrySet();
				for (Entry<String, JsonValue> entry : entrySet) {
					String name = entry.getKey();
					String value = StringUtils.trimQuotation(entry.getValue().toString());

					objBuilder.add(name, value);

					objMap.put(name, value);
				}

				this.jsonList.add(objMap);
			}

			this.builder.add(objBuilder);
		}

		reader.close();
		//jsonArray = arr;

		// loop value
/*		Map<String, String> tempJsonMap = new HashMap<String, String>();
		Set<Entry<String, JsonValue>> entrySet = obj.entrySet();
		for (Entry<String, JsonValue> entry : entrySet) {
			String name = entry.getKey();
			// the value string is surrounded by quotations
			// so we need to trim them off
			String value = StringUtils.trimQuotation(entry.getValue().toString());

			tempJsonMap.put(name, value);
		}


		this.jsonMap = tempJsonMap;*/
	}
}
