# Docker - Guia de Uso

## Build da Imagem

```bash
docker build -t sf-tech-sales-api:latest .
```

## Executar Localmente

### Usando Docker diretamente:

```bash
docker run -d \
  --name sf-tech-sales-api \
  -p 8080:8080 \
  -e DATABASE_URL="jdbc:postgresql://seu-host:5432/seu-db" \
  -e DATABASE_USERNAME="seu-usuario" \
  -e DATABASE_PASSWORD="sua-senha" \
  sf-tech-sales-api:latest
```

### Usando Docker Compose:

**Opção 1 - Comando moderno (recomendado):**
```bash
docker compose up -d
```

**Opção 2 - Comando antigo:**
```bash
docker-compose up -d
```

Para ver os logs:
```bash
docker compose logs -f
# ou
docker-compose logs -f
```

Para parar:
```bash
docker compose down
# ou
docker-compose down
```

### Alternativa - Script direto:

Se tiver problemas com docker-compose, use o script:
```bash
./docker-run.sh
```

## Deploy no Render.com

1. Conecte seu repositório GitHub ao Render
2. Configure as variáveis de ambiente no dashboard do Render:
   - `DATABASE_URL`
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`
3. Configure o Dockerfile como caminho de build
4. Render detectará automaticamente o Dockerfile e fará o build

### Configurações Recomendadas no Render:
- **Build Command**: (deixe vazio, o Dockerfile já faz o build)
- **Start Command**: (deixe vazio, o Dockerfile já define o ENTRYPOINT)
- **Port**: 8080
- **Health Check Path**: `/actuator/health`

## Variáveis de Ambiente

Certifique-se de configurar:
- `DATABASE_URL`: URL completa do banco PostgreSQL
- `DATABASE_USERNAME`: Usuário do banco
- `DATABASE_PASSWORD`: Senha do banco

