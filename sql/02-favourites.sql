CREATE TABLE IF NOT EXISTS public.Favourites (
    login              text NOT NULL REFERENCES public.User(login) ON DELETE CASCADE
    , product_id       uuid NOT NULL REFERENCES public.Product(id) ON DELETE CASCADE
    , primary key(login, product_id)
);
