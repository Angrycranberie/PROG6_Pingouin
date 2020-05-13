package controller.ai;

import controller.ai.ConfigVector;

public class ConfigNode {
	private int value;
	private ConfigVector config;
	
	public ConfigNode(int val, ConfigVector config) {
		this.value = val;
		this.config = config;
	}
	
	public int getValue() {
		return value;
	}
	
	public ConfigVector getConfig() {
		return config;
	}
	
	public void setValue(int v){
		value = v;
	}
	
}
