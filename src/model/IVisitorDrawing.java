package model;

public interface IVisitorDrawing {
	void visit( ComposeFigure figure );
	void visit( Ellipse figure );
	void visit( Line figure );
	void visit( Rectangle figure );
	void visit( Text figure );
	void visit( Triangle figure );
}
