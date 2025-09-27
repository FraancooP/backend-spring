package ar.edu.iua.iw3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Dos cosas obligatorias cuando uno define una entidad, 1 marcar como Entity, 2 Definir la clave principal!!

@Entity
@Table(name="products")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, unique=true)
	private String product;
	
	@Column(columnDefinition = "tinyint default 0")
	private boolean stock = false;
	
	private double precio;
	
	@ManyToOne
	@JoinColumn(name="id_category", nullable = true)
	private Category categori;
}
