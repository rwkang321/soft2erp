insert into sys_common_code_group (code_group, code_group_name, description, sort_order, created_by)
values
    ('ITEM_TYPE', 'Item Type', 'Item classification', 10, 'system'),
    ('UNIT', 'Unit', 'Base unit', 20, 'system'),
    ('USE_YN', 'Use Y/N', 'Use flag', 30, 'system')
on conflict (code_group) do nothing;

insert into sys_common_code (code_group, code, code_name, sort_order, created_by)
values
    ('ITEM_TYPE', 'RAW', 'Raw Material', 10, 'system'),
    ('ITEM_TYPE', 'HALF', 'Semi Finished Goods', 20, 'system'),
    ('ITEM_TYPE', 'FIN', 'Finished Goods', 30, 'system'),
    ('UNIT', 'EA', 'Each', 10, 'system'),
    ('UNIT', 'KG', 'Kilogram', 20, 'system'),
    ('UNIT', 'M', 'Meter', 30, 'system'),
    ('USE_YN', 'Y', 'Use', 10, 'system'),
    ('USE_YN', 'N', 'Not Use', 20, 'system')
on conflict (code_group, code) do nothing;

