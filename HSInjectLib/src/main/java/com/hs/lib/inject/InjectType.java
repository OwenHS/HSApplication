package com.hs.lib.inject;


public enum InjectType {

	// 用于查找Activity
	ACTIVITY {
		@Override
		public String getJudgement() {
			return "if(type == InjectType.ACTIVITY){\n";
		}
	},
	VIEW {
		@Override
		public String getJudgement() {
			return "}else if(type == InjectType.VIEW){\n";
		}
	};

	public String getJudgement(){
		return null;
	}

}
