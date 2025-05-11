create view accumulative_profit_report_view as
with aggregate_by_day as (
    select sum(p.amount) / c.initial_amount as profit, p.created_at as day
    from credits c inner join payments p on c.id = p.credit_id
    group by c.id, p.created_at
), accumulate_by_day as (
    select sum(profit) over (partition by extract(year from day), extract(month from day) order by extract(day from day)) as profit,
           day as day,
           extract(month from day) as month,
           extract(year from day) as year,
           DATE(day)::text as date_t
    from aggregate_by_day
), accumulate_by_month as (
    select
--         distinct on (extract(month from day)) extract(month from day),
        sum(profit) over (partition by extract(year from day) order by extract(month from day)) as profit,
        day + interval '32 days' as day,
        extract(month from day) as month,
        extract(year from day) as year,
        extract(month from day)::text || '-' || extract(year from day)::text as date_t
    from aggregate_by_day
), accumulate_by_year as (
    select
        distinct on (extract(year from day)) extract(year from day),
        sum(profit) over (order by extract(year from day)) as profit,
        day + interval '2 years' as day,
        12 as month,
        extract(year from day) as year,
        extract(year from day)::text as date_t
    from aggregate_by_day
), order_by_date as (
    select profit, date_t, day, month, year from accumulate_by_day
        union all
    select profit, date_t, day, month, year from accumulate_by_month
        union all
    select profit, date_t, day, month, year from accumulate_by_year
    order by year, month, day
) select profit, date_t, row_number() over () as id from order_by_date;