package com.tequeno.lucky;

public enum Rules {
	ONE("6+1", "一等奖￥^-^"), 
	TWO("6+0", "二等奖￥^-^"), 
	THREE("5+1", "三等奖￥3000"),
	FOUR("5+0", "四等奖￥200"), 
	FOUR_PLUS("4+1", "四等奖￥200"),
	FIVE("4+0","五等奖￥10"), 
	FIVE_PLUS("3+1", "五等奖￥10"),
	SIX("2+1", "六等奖￥5"), 
	SIX_PLUS("1+1", "六等奖￥5"), 
	SIX_PP("0+1", "六等奖￥5"),
	NO_LUCK_1("3+0","未中奖￥0"),
	NO_LUCK_2("2+0","未中奖￥0"),
	NO_LUCK_4("1+0","未中奖￥0"),
	NO_LUCK_5("0+0","未中奖￥0");


	private String desc;
	
	private String result;

	private Rules(String desc, String result) {
		this.desc = desc;
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
