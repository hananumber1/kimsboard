package com.kimscooperation.kimsboard.domain.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "board")
public class Board {
	
	@Id
	@Column(name = "board_num")
	@SequenceGenerator(name = "board_seq_generator", sequenceName = "board_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
	private long boardNum;
	
	@Column(nullable = false, length = 100)
	private String name;

	//고집좀 부리지마
}
