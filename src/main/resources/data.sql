-- 1. Materials
INSERT INTO materials (id, name, unit) VALUES
                                           ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Gold-Titanium Alloy', 'kg'),
                                           ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Vibranium', 'kg'),
                                           ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Palladium', 'grams');

-- 2. Inventory (Stocking the warehouse)
INSERT INTO inventory (id, material_id, stock_quantity, last_updated) VALUES
                                                                          (random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 500.0, CURRENT_TIMESTAMP),
                                                                          (random_uuid(), 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 15.0, CURRENT_TIMESTAMP),
                                                                          (random_uuid(), 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 250.0, CURRENT_TIMESTAMP);

-- 3. Components
INSERT INTO components (id, name, category) VALUES
                                                ('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Hand Repulsor', 'WEAPONRY'),
                                                ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Arc Reactor Core', 'POWER');

-- 4. Component Materials (The Join Table connecting Components -> Materials)
-- Arc Reactor (e0ee...) needs 50g Palladium (c0ee...)
-- Hand Repulsor (d0ee...) needs 1.5kg Gold-Titanium (a0ee...)
INSERT INTO component_materials (id, component_id, material_id, amount_required) VALUES
                                                                                     (random_uuid(), 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 50.0),
                                                                                     (random_uuid(), 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1.5);

-- 5. Suits
INSERT INTO suits (id, designation, version, status) VALUES
                                                         ('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Mark III', '3.0.0', 'OPERATIONAL'),
                                                         ('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Mark 42', '42.0.0', 'TESTING');

-- 6. Suit Components (The Join Table connecting Suits -> Components)
-- Mark III (f0ee...16) needs 1 Arc Reactor (e0ee...) and 2 Repulsors (d0ee...)
-- Mark 42 (f0ee...17) needs 1 Arc Reactor (e0ee...) and 4 Repulsors (d0ee...)
INSERT INTO suit_components (id, suit_id, component_id, quantity_required) VALUES
                                                                               (random_uuid(), 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 1),
                                                                               (random_uuid(), 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 2),
                                                                               (random_uuid(), 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 1),
                                                                               (random_uuid(), 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 4);