INSERT INTO emotions (name, description, created_at, updated_at, created_id, updated_id)
VALUES ('SOLITUDE', '홀로 있음에서 오는 조용한 깊이', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('FEAR', '다가올 위협에 대한 불안한 마음', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('LETHARGY', '아무것도 할 수 없을 만큼 지친 상태', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('SHAME', '자신이 드러나는 것이 민망하고 불편한 감정', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('ANGER', '참을 수 없을 만큼 격해진 감정', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('SADNESS', '마음이 아프고 눈물이 나는 감정', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('PAIN', '몸이나 마음이 상처받아 괴로운 느낌', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('LONELINESS', '누군가를 그리워하며 느끼는 공허함', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('JEALOUSY', '남의 것을 부러워하고 빼앗고 싶은 감정', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('HATRED', '싫고 멀리하고 싶은 강한 감정', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('CONFUSION', '어떻게 해야 할지 몰라 머릿속이 복잡한 상태', NOW(), NOW(), 'manual_admin', 'manual_admin');

INSERT INTO fairies (name, emotion_id, image_url, silhouette_image_url, created_at, updated_at, created_id, updated_id)
VALUES ('그림자 요정', (SELECT id FROM emotions WHERE name = 'SOLITUDE'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/solitude.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/solitude_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('빼꼼 요정', (SELECT id FROM emotions WHERE name = 'FEAR'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/fear.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/fear_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('흐물흐물 요정', (SELECT id FROM emotions WHERE name = 'LETHARGY'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/lethargy.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/lethargy_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('발그레 요정', (SELECT id FROM emotions WHERE name = 'SHAME'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/shame.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/shame_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('화르륵 요정', (SELECT id FROM emotions WHERE name = 'ANGER'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/anger.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/anger_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('눈물방울 요정', (SELECT id FROM emotions WHERE name = 'SADNESS'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/sadness.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/sadness_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('아야야 요정', (SELECT id FROM emotions WHERE name = 'PAIN'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/pain.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/pain_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('웅클 요정', (SELECT id FROM emotions WHERE name = 'LONELINESS'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/loneliness.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/loneliness_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('찌릿 요정', (SELECT id FROM emotions WHERE name = 'JEALOUSY'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/jealousy.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/jealousy_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('찡글 요정', (SELECT id FROM emotions WHERE name = 'HATRED'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/hatred.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/hatred_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin'),
       ('갸우뚱 요정', (SELECT id FROM emotions WHERE name = 'CONFUSION'), 'https://kr.object.ncloudstorage.com/gamchi/fairies/confusion.png', 'https://kr.object.ncloudstorage.com/gamchi/fairies/confusion_silhouette.png', NOW(), NOW(), 'manual_admin', 'manual_admin');