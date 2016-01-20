package model;

import controller.App;

public class Undo extends ReUnStrategy {

	@Override
	public void changeCanvas() {
		App.getInstance().undo();
	}

}
