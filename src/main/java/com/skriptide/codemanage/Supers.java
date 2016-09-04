package com.skriptide.codemanage;

import com.skriptide.main.Main;

import java.util.ArrayList;

/**
 * Created by Liz3ga on 01.09.2016.
 */
public class Supers {

	private ArrayList<String> superV = new ArrayList<>();
	private String[] supervArray;

	public Supers() {

		String[] list = new String[]{
				"if", "else", "loop", "trigger", "stop", "set"
		};
		for (int i = 0; i != list.length; i++) {
			superV.add(i, list[i]);
		}
		supervArray = list;

		if(Main.debugMode) {
			System.out.println("Loaded supers!");
		}

	}

	public String[] getSupervArray() {
		return this.supervArray;
	}


}
