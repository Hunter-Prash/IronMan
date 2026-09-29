--The Inventory Subsystem: Materials Inventory (tracking raw resources in stock).--

-- The Manufacturing Subsystem: Suits Suit_Component Components (building armor out of physical components like Repulsors, Arc Reactors, Thrusters).--



-- 1. Materials
CREATE TABLE IF NOT EXISTS materials (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    unit VARCHAR(20) NOT NULL
    );


-- 2. Inventory (Stock levels per material)
CREATE TABLE IF NOT EXISTS inventory (
    id UUID PRIMARY KEY,
    material_id UUID NOT NULL UNIQUE,
    stock_quantity DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    last_updated TIMESTAMP NOT NULL,
    CONSTRAINT fk_inventory_material FOREIGN KEY (material_id) REFERENCES materials(id) ON DELETE RESTRICT
    );


-- 3. Components (Sub-assemblies like Repulsor, HUD)
CREATE TABLE IF NOT EXISTS components (
     id UUID PRIMARY KEY,
     name VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(50) NOT NULL
    );

-- 5. Suits (The armor models)
CREATE TABLE IF NOT EXISTS suits (
    id UUID PRIMARY KEY,
    designation VARCHAR(50) NOT NULL UNIQUE,
    version VARCHAR(20),
    status VARCHAR(30) NOT NULL
    );


-- 6. Suit Components (Join Table with Payload: quantity of components per suit)
CREATE TABLE IF NOT EXISTS suit_components (
      id UUID PRIMARY KEY,
      suit_id UUID NOT NULL,
      component_id UUID NOT NULL,
      quantity_required INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_sc_suit FOREIGN KEY (suit_id) REFERENCES suits(id) ON DELETE CASCADE,
    CONSTRAINT fk_sc_component FOREIGN KEY (component_id) REFERENCES components(id) ON DELETE RESTRICT,
    CONSTRAINT uq_suit_component UNIQUE (suit_id, component_id)
    );