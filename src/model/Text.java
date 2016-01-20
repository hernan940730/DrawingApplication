package model;

import java.awt.Graphics2D;

import javax.swing.JTextField;

//TODO JTextField or Figure?? 
public class Text extends Figure implements IFigure{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField = new JTextField();
	
	public Text( BoundBox bbox ){
		super( bbox );
	}

	protected void doPaint( Graphics2D g ) {
		textField.setVisible( false );
		textField.setBounds( bbox );
		textField.setVisible( true );
	}
	
	public IFigure clone(){
		return new Text( new BoundBox( bbox ) );
	}

	@Override
	public void accept(IVisitorDrawing vd) {
		vd.visit( this );
		
	}
}
