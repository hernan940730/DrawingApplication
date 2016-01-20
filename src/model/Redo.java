package model;

import controller.App;

public class Redo extends ReUnStrategy {

	@Override
	public void changeCanvas() {
		
		App.getInstance().redo();
	}

}
