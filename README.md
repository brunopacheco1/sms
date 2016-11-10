# Serviço de Envio de SMS

Projeto RESTFul para envio de SMS. Desenvolvido em Java, utilizando a especificação JavaEE 7 (JAX-RS, EJB e JPA).

Ambiente:
- Hardware: 613MB de RAM, 10GB de HD, 1 vCPU de 1.0GHz a 1.2GHz, Amazon EC2 t1.micro
- SO: Ubuntu 14.04, AMI Bitnami Wildfly 9.x
- Banco de Dados: MySQL 5.6
- Servidor de Aplicação: Wildfly 9.0.2.Final
- Java: 1.8
- Mensageria: RabbitMQ-Server 3.6.5
- Endereço: http://ec2-54-167-228-148.compute-1.amazonaws.com/service-sms/
- Swagger: http://ec2-54-167-228-148.compute-1.amazonaws.com/service-sms/api/swagger.yaml

Deploy:
- Ter o java, servidor de aplicação, banco de dados e mensageria instalados;
- Configurar no Wildfly o driver do MySQL;
- Configurar no Wildfly o datasource de acesso ao banco da aplicação com o JNDI "java:/smsDS";
- Criar no MySQL o banco de dados SMS;
- Criar a fila "java/service/sms", com o parâmetro durable=true, no RabbitMQ-Server;

Teste de carga:
- Ter o JMeter;
- Executar o teste;