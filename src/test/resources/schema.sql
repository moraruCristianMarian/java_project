
CREATE TABLE IF NOT EXISTS public.restaurants
(
    id integer NOT NULL DEFAULT nextval('restaurants_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    opening_time time without time zone NOT NULL,
    closing_time time without time zone NOT NULL,
    CONSTRAINT restaurants_pkey PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS public.products
(
    id integer NOT NULL DEFAULT nextval('products_id_seq'::regclass),
    product_category_id integer NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    cost double precision NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id),
    CONSTRAINT "products_FK_product_categories" FOREIGN KEY (product_category_id)
        REFERENCES public.product_categories (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

CREATE INDEX IF NOT EXISTS "fki_FK_product_category"
    ON public.products USING btree
    (product_category_id ASC NULLS LAST)
    TABLESPACE pg_default;


CREATE TABLE IF NOT EXISTS public.ingredients
(
    id integer NOT NULL DEFAULT nextval('ingredients_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    is_meat boolean NOT NULL,
    is_dairy boolean NOT NULL,
    is_gluten boolean NOT NULL,
    CONSTRAINT ingredients_pkey PRIMARY KEY (id)
)


CREATE TABLE IF NOT EXISTS public.product_categories
(
    id integer NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT product_categories_pkey PRIMARY KEY (id)
)


CREATE TABLE IF NOT EXISTS public.ingredient_in_products
(
    ingredient_id integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT ingredient_in_products_pkey PRIMARY KEY (ingredient_id, product_id),
    CONSTRAINT "ingredient_in_products_FK_ingredients" FOREIGN KEY (ingredient_id)
        REFERENCES public.ingredients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "ingredient_in_products_FK_products" FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)


CREATE TABLE IF NOT EXISTS public.menu_products
(
    product_id integer NOT NULL,
    restaurant_id integer NOT NULL,
    discount double precision NOT NULL,
    promotion_end_date timestamp without time zone NOT NULL,
    CONSTRAINT menu_products_pkey PRIMARY KEY (product_id, restaurant_id),
    CONSTRAINT "menu_products_FK_products" FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "menu_products_FK_restaurants" FOREIGN KEY (restaurant_id)
        REFERENCES public.restaurants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)