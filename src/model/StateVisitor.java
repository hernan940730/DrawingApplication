package model;

import controller.App;

public class StateVisitor implements IVisitorDrawing {

	@Override
	public void visit(ComposeFigure figure) {
		App.getInstance().setStatusBar("Group", figure.getBoundBox().x + "", figure.getBoundBox().y + "", 
				figure.getBoundBox().width + "", figure.getBoundBox().height + "", figure.getChildrens( ).size()+"", "          ");
	}

	@Override
	public void visit( Ellipse figure ) {
		defaultGeometricFigure( figure, "Ellipse" );	
	}

	@Override
	public void visit( Line figure ) {
		BoundBox bbox = figure.getBoundBox();
		App.getInstance().setStatusBar("Line", figure.getBoundBox().x + "", figure.getBoundBox().y + "", 
				"          ", "          ", "          ", Math.sqrt( bbox.height * bbox.height + bbox.width * bbox.width )+"");
	}

	@Override
	public void visit(Rectangle figure) {
		defaultGeometricFigure(figure, "Rectangle");
	}
	
	@Override
	public void visit(Triangle figure) {
		defaultGeometricFigure(figure, "Triangle");
	}

	@Override
	public void visit( Text figure ) {
		// TODO Visit to text
	}
	
	private void defaultGeometricFigure(IFigure figure, String name ){
		App.getInstance().setStatusBar(name, figure.getBoundBox().x+"", figure.getBoundBox().y+"", 
				figure.getBoundBox().width+"", figure.getBoundBox().height+"", "          ", "          ");
		
	}
	
}
