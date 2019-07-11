CREATE TABLE empresa (
  id bigint NOT NULL,
  cnpj varchar(255) NOT NULL,
  data_atualizacao timestamp(0) NOT NULL,
  data_criacao timestamp(0) NOT NULL,
  razao_social varchar(255) NOT NULL
) ;

CREATE TABLE funcionario (
  id bigint NOT NULL,
  cpf varchar(255) NOT NULL,
  data_atualizacao timestamp(0) NOT NULL,
  data_criacao timestamp(0) NOT NULL,
  email varchar(255) NOT NULL,
  nome varchar(255) NOT NULL,
  perfil varchar(255) NOT NULL,
  qtd_horas_almoco double precision DEFAULT NULL,
  qtd_horas_trabalho_dia double precision DEFAULT NULL,
  senha varchar(255) NOT NULL,
  valor_hora decimal(19,2) DEFAULT NULL,
  empresa_id bigint DEFAULT NULL
) ;

CREATE TABLE lancamento (
  id bigint NOT NULL,
  data timestamp(0) NOT NULL,
  data_atualizacao timestamp(0) NOT NULL,
  data_criacao timestamp(0) NOT NULL,
  descricao varchar(255) DEFAULT NULL,
  localizacao varchar(255) DEFAULT NULL,
  tipo varchar(255) NOT NULL,
  funcionario_id bigint DEFAULT NULL
) ;

--
-- Indexes for table `empresa`
--
ALTER TABLE empresa
  ADD PRIMARY KEY (id);

--
-- Indexes for table `funcionario`
--
ALTER TABLE funcionario
  ADD PRIMARY KEY (id),
  ADD KEY `FK4cm1kg523jlopyexjbmi6y54j` (empresa_id);

--
-- Indexes for table `lancamento`
--
ALTER TABLE lancamento
  ADD PRIMARY KEY (id),
  ADD KEY `FK46i4k5vl8wah7feutye9kbpi4` (funcionario_id);

--
-- AUTO_INCREMENT for table `empresa`
--
ALTER TABLE empresa
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `funcionario`
--
ALTER TABLE funcionario
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lancamento`
--
ALTER TABLE lancamento
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `funcionario`
--
ALTER TABLE funcionario
  ADD CONSTRAINT FK4cm1kg523jlopyexjbmi6y54j FOREIGN KEY (empresa_id) REFERENCES empresa (id);

--
-- Constraints for table `lancamento`
--
ALTER TABLE lancamento
  ADD CONSTRAINT FK46i4k5vl8wah7feutye9kbpi4 FOREIGN KEY (funcionario_id) REFERENCES funcionario (id);