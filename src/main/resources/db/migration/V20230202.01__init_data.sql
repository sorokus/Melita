INSERT INTO public.product (id, name, description) VALUES (1, 'Internet', 'Fiber-optic internet');
INSERT INTO public.product (id, name, description) VALUES (2, 'TV', 'Digital TV');
INSERT INTO public.product (id, name, description) VALUES (3, 'Telephony', 'Wire or wireless communication');
INSERT INTO public.product (id, name, description) VALUES (4, 'Mobile', 'Mobile (cellular) communication');

INSERT INTO public.package (id, name, product_id, description) VALUES (1, '250Mbps', 1, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (2, '1Gbps', 1, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (3, '90 Channels', 2, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (4, '140 Channels', 2, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (5, 'Free On net Calls', 3, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (6, 'Unlimited Calls', 3, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (8, 'Post-paid', 4, null);
INSERT INTO public.package (id, name, product_id, description) VALUES (7, 'Prepaid', 4, null);

