-- 1. fairies.id를 참조하는 외래 키 제약 조건 삭제
ALTER TABLE acquired_fairies DROP FOREIGN KEY acquired_fairies_ibfk_2;
ALTER TABLE letters DROP FOREIGN KEY letters_ibfk_2;

-- 2. fairies 테이블의 id 컬럼을 AUTO_INCREMENT로 수정
ALTER TABLE fairies MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

-- 3. 삭제했던 외래 키 제약 조건 다시 추가
ALTER TABLE acquired_fairies
    ADD CONSTRAINT acquired_fairies_ibfk_2
        FOREIGN KEY (fairy_id) REFERENCES fairies (id);
ALTER TABLE letters
    ADD CONSTRAINT letters_ibfk_2
        FOREIGN KEY (fairy_id) REFERENCES fairies (id);