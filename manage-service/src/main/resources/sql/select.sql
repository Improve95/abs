select count(*) as total, count(p.status = 'NOT_PAID') as expired from credits c
    left join penalties p on c.id = p.credit_id;

select sum_initial_amount / sum_payed_amount as profit from (
    select sum(c.initial_amount) as sum_initial_amount, sum(p.amount) as sum_payed_amount
    from credits c inner join payments p on c.id = p.credit_id
    group by c.id
) as siaspa;