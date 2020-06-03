USE UTNPhones;

-- Users
INSERT INTO `users` (`id`, `id_city`, `user_name`, `user_lastname`, `user_idcard`, `user_password`, `user_role`) VALUES
(1, 401, 'Jose', 'Ledesma', 3231123, '$2a$10$ozCc3JsOHl1FZowdlsnY3ejhhwlCZALx1uaROxjo6cIVGO9O4DdsO', 'USER'),
(2, 40, 'Manuela', 'Suarez', 3231137, '$2a$10$ozCc3JsOHl1FZowdlsnY3ejhhwlCZALx1uaROxjo6cIVGO9O4DdsO', 'USER'),
(3, 82, 'Luis', 'Montes', 3231198, '$2a$10$ozCc3JsOHl1FZowdlsnY3ejhhwlCZALx1uaROxjo6cIVGO9O4DdsO', 'USER'),
(4, 33, 'Juan', 'Saenz', 3248921, '$2a$10$ozCc3JsOHl1FZowdlsnY3ejhhwlCZALx1uaROxjo6cIVGO9O4DdsO', 'EMPLOYEE'),
(5, 122, 'Pablo', 'Perez', 3545221, '$2a$10$ozCc3JsOHl1FZowdlsnY3ejhhwlCZALx1uaROxjo6cIVGO9O4DdsO', 'INFRAESTRUCTURE');

-- Phonelines
INSERT INTO `phonelines` (`id`, `id_user`, `id_city`, `phoneline_type`, `phoneline_status`) VALUES
(2235628837, 1, 401, 'MOBILE', 'ACTIVE'),
(2235628838, 2, 22, 'MOBILE', 'ACTIVE'),
(2235628839, 3, 312, 'MOBILE', 'ACTIVE');

-- Calls
INSERT INTO `calls` (`id_line_origin`, `id_line_destination`, `call_duration`) VALUES
(2235628837,2235628838,110),
(2235628837,2235628838,10),
(2235628838,2235628837,145),
(2235628837,2235628839,157),
(2235628839,2235628837,120),
(2235628838,2235628837,150);