create table exchange_rate
(
	id uuid not null
		constraint exchange_rate_pkey
			primary key,
    date date not null,
    provider_id varchar(255),
    multiplier numeric(19,2),
    orig_currency varchar(255) not null,
    currency varchar(255) not null,
	rate numeric(12,6) not null,
    created_at timestamp
);


