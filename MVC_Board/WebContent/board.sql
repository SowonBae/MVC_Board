show tables;


CREATE TABLE board(
	 board_num INT PRIMARY KEY,
	 board_name VARCHAR(20) NOT NULL,
	 board_pass VARCHAR(15) NOT NULL,
	 board_subject VARCHAR(50) NOT NULL,
	 board_content VARCHAR(2000) NOT NULL,
	 board_file VARCHAR(50) NOT NULL,
	 board_re_ref INT NOT NULL,
	 board_re_lev INT NOT NULL,
	 board_re_seq INT NOT NULL,
	 board_readcount INT DEFAULT 0,
	 board_date DATE
);


desc board;

select * from board;

select * from board order by board_re_ref desc, board_re_seq asc limit 1,10;

update board set board_readcount = board_readcount+1 where board_num =1;


CREATE TABLE member(
	 idx INT PRIMARY KEY auto_increment,
	 name VARCHAR(10) NOT NULL,
	 id VARCHAR(12) UNIQUE NOT NULL,
	 passwd VARCHAR(16) NOT NULL,
	 email VARCHAR(50) UNIQUE NOT NULL,
	 regDate DATE NOT NULL
);




























