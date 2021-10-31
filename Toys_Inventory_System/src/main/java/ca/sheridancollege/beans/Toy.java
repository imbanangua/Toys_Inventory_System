package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Toy implements java.io.Serializable {

	private static final long serialVersionUID = 2691619960097251753L;
	
	private int id;
	private String name;
	private double price;
	private int quantity;
	
}
