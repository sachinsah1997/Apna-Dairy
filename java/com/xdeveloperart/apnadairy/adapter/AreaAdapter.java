package com.xdeveloperart.apnadairy.adapter;

public class AreaAdapter {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
        private static AreaAdapter instance;

	public AreaAdapter(){}

        public static synchronized AreaAdapter getInstance(){
            if(instance==null){
                instance=new AreaAdapter();
            }
            return instance;
        }

        public static void setInstance(AreaAdapter instance) {
            AreaAdapter.instance = instance;
        }

  	public AreaAdapter(String name) {
        	this.name=name;
	}
}


