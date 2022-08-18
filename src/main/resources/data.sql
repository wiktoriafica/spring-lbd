INSERT INTO attachments(attachment, content_type, file_name) VALUES
(FILE_READ('src/main/resources/image.png'), 'image/png', 'image.png'),
(FILE_READ('src/main/resources/image.jpg'), 'image/jpeg', 'image.jpg'),
(FILE_READ('src/main/resources/text.txt'), 'text/plain', 'text.txt');

INSERT INTO sprints(name, start_date, end_date, goal_description, sprint_status) VALUES
	('Server', '2022-05-21', '2022-06-15', 'To finish all tasks', 'FINISHED');
SET @sprint_id = SELECT max(sprint_id) FROM sprints;
INSERT INTO user_stories(name, description, story_points, user_story_status) VALUES
	('Task1', 'Added first task', 10, 'TO_DO');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);
INSERT INTO user_stories_attachments(user_story_id, attachment_id) VALUES(@user_story_id, 1);

INSERT INTO user_stories(name, description, story_points, user_story_status) VALUES
	('Task2', 'Added second task', 3, 'IN_PROGRESS');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);
INSERT INTO user_stories_attachments(user_story_id, attachment_id) VALUES(@user_story_id, 1);
INSERT INTO user_stories(name, description, story_points, user_story_status) VALUES
	('Task3', 'Added third task', 8, 'REVIEW');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);
INSERT INTO user_stories_attachments(user_story_id, attachment_id) VALUES(@user_story_id, 2);


INSERT INTO user_stories(name, description, story_points, user_story_status) VALUES
	('Task4', 'Added fourth task', 15, 'DONE');
SET @user_story_id = SELECT max(user_story_id) FROM user_stories;
INSERT INTO user_stories_in_sprints VALUES(@sprint_id, @user_story_id);
INSERT INTO user_stories_attachments(user_story_id, attachment_id) VALUES(@user_story_id, 3);
