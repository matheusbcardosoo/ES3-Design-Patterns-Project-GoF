package org.fatec.facade;

import org.fatec.domain.*;
import org.fatec.domain.adapter.SistemaPagamento;
import org.fatec.domain.adapter.SistemaPagamentoAdapter;
import org.fatec.domain.adapter.SistemaPagamentoExterno;
import org.fatec.domain.builder.animal.AnimalCreator;
import org.fatec.domain.builder.animal.CachorroBuilder;
import org.fatec.domain.builder.animal.GatoBuilder;
import org.fatec.domain.builder.consulta.ConsultaBuilder;
import org.fatec.domain.builder.pessoa.ClienteBuilder;
import org.fatec.domain.builder.pessoa.PessoaCreator;
import org.fatec.domain.builder.pessoa.VeterinarioBuilder;
import org.fatec.domain.singleton.ClinicaVeterinaria;

import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoFacade {
    private final ClienteBuilder clienteBuilder;
    private final VeterinarioBuilder veterinarioBuilder;
    private final CachorroBuilder cachorroBuilder;
    private final GatoBuilder gatoBuilder;
    private final ConsultaBuilder consultaBuilder;
    private final ClinicaVeterinaria clinica;
    private final SistemaPagamento sistemaPagamento; // Sistema de pagamento adaptado

    public AgendamentoFacade() {
        this.clienteBuilder = new ClienteBuilder();
        this.veterinarioBuilder = new VeterinarioBuilder();
        this.cachorroBuilder = new CachorroBuilder();
        this.gatoBuilder = new GatoBuilder();
        this.consultaBuilder = new ConsultaBuilder();
        this.clinica = ClinicaVeterinaria.getInstance();

        // Inicializando o sistema de pagamento usando o Adapter
        SistemaPagamentoExterno sistemaExterno = new SistemaPagamentoExterno();
        this.sistemaPagamento = new SistemaPagamentoAdapter(sistemaExterno);
    }

    public void agendarConsulta(
            String nomeCliente,
            String enderecoCliente,
            String telefoneCliente,
            String nomeAnimal,
            String tipoAnimal,
            String generoAnimal,
            int idadeAnimal,
            double pesoAnimal,
            String corAnimal,
            String nomeVeterinario,
            String crmv,
            String telefoneVeterinario,
            LocalDateTime dataHoraConsulta,
            String diagnosticoInicial,
            String medicamentoInicial
    ) {
        System.out.println("Iniciando agendamento de consulta...");

        // Criar Cliente com todos os atributos
        clienteBuilder.comNome(nomeCliente)
                     .comTelefone("11", telefoneCliente);

        // Criar e configurar o endereço completo do cliente
        Endereco endereco = new Endereco();
        String[] partesEndereco = enderecoCliente.split(",", -1);

        // Garantir que todas as partes do endereço sejam preenchidas
        endereco.setCep(partesEndereco.length > 0 ? partesEndereco[0].trim() : "");
        endereco.setLogradouro(partesEndereco.length > 1 ? partesEndereco[1].trim() : "");
        endereco.setNumero(partesEndereco.length > 2 ? partesEndereco[2].trim() : "");
        endereco.setComplemento(partesEndereco.length > 3 ? partesEndereco[3].trim() : "");
        endereco.setBairro(partesEndereco.length > 4 ? partesEndereco[4].trim() : "");
        endereco.setCidade(partesEndereco.length > 5 ? partesEndereco[5].trim() : "");
        endereco.setEstado(partesEndereco.length > 6 ? partesEndereco[6].trim() : "");
        endereco.setPais(partesEndereco.length > 7 ? partesEndereco[7].trim() : "Brasil");

        clienteBuilder.comEndereco(endereco);

        // Processar a criação do cliente
        PessoaCreator<Cliente> clienteCreator = new PessoaCreator<>(clienteBuilder);
        clienteCreator.criarPessoa();
        Cliente cliente = clienteCreator.getPessoa();

        // Criar Animal com todos os atributos
        Animal animal;
        if ("Cachorro".equalsIgnoreCase(tipoAnimal)) {
            cachorroBuilder.comNome(nomeAnimal)
                          .comGenero(generoAnimal)
                          .comIdade(idadeAnimal)
                          .comPeso(pesoAnimal)
                          .comCor(corAnimal);

            AnimalCreator<Cachorro> animalCreator = new AnimalCreator<>(cachorroBuilder);
            animalCreator.criarAnimal();
            animal = animalCreator.getAnimal();
        } else {
            gatoBuilder.comNome(nomeAnimal)
                      .comGenero(generoAnimal)
                      .comIdade(idadeAnimal)
                      .comPeso(pesoAnimal)
                      .comCor(corAnimal);

            AnimalCreator<Gato> animalCreator = new AnimalCreator<>(gatoBuilder);
            animalCreator.criarAnimal();
            animal = animalCreator.getAnimal();
        }

        // Criar Veterinário com todos os atributos
        veterinarioBuilder.comNome(nomeVeterinario)
                         .comCRMV(crmv)
                         .comTelefone("11", telefoneVeterinario);

        PessoaCreator<Veterinario> veterinarioCreator = new PessoaCreator<>(veterinarioBuilder);
        veterinarioCreator.criarPessoa();
        Veterinario veterinario = veterinarioCreator.getPessoa();

        // Criar Consulta completa e agendar
        String dataFormatada = dataHoraConsulta.toString();

        // Agendar consulta na clínica
        clinica.agendarConsulta(animal, veterinario, dataFormatada);

        // Adicionar diagnóstico e medicamento, se fornecidos
        if (diagnosticoInicial != null && !diagnosticoInicial.isEmpty()) {
            clinica.emitirDiagnostico(animal, diagnosticoInicial);
        }

        if (medicamentoInicial != null && !medicamentoInicial.isEmpty()) {
            clinica.prescreverMedicamento(animal, medicamentoInicial);
        }

        System.out.println("Consulta agendada com sucesso para " + nomeCliente);
        System.out.println("Detalhes: Animal: " + nomeAnimal + " (" + tipoAnimal + "), Veterinário: " + nomeVeterinario);
        System.out.println("Data e hora: " + dataFormatada);
    }

    public List<Consulta> listarConsultas() {
        System.out.println("Listando todas as consultas agendadas...");
        List<Consulta> consultas = clinica.getConsultas();

        if (consultas.isEmpty()) {
            System.out.println("Não há consultas agendadas.");
        } else {
            System.out.println("Total de consultas: " + consultas.size());
            for (int i = 0; i < consultas.size(); i++) {
                Consulta c = consultas.get(i);
                System.out.println("Consulta " + (i+1) + ":");
                System.out.println("  Animal: " + c.getPet().getNome() + " (" + c.getPet().getEspecie() + ")");
                System.out.println("  Veterinário: " + c.getVeterinario().getNome() + " (CRMV: " + c.getVeterinario().getCrmv() + ")");
                System.out.println("  Data: " + c.getData());
                System.out.println("  Diagnóstico: " + (c.getDiagnostico() != null ? c.getDiagnostico() : "Não realizado"));
                System.out.println("  Medicamento: " + (c.getMedicamento() != null ? c.getMedicamento() : "Não prescrito"));
                System.out.println("---------------------------");
            }
        }

        return consultas;
    }

    /**
     * Realiza o pagamento de uma consulta
     * @param consulta a consulta a ser paga
     * @param valor o valor do pagamento
     * @return true se o pagamento foi processado com sucesso
     */
    public boolean realizarPagamentoConsulta(Consulta consulta, double valor) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("PROCESSANDO PAGAMENTO DE CONSULTA");
        System.out.println("------------------------------------------------------------------------");

        String descricao = "Consulta para " + consulta.getPet().getNome() +
                          " com Dr(a). " + consulta.getVeterinario().getNome() +
                          " em " + consulta.getData();

        try {
            sistemaPagamento.realizarPagamento(valor, descricao);
            System.out.println("Pagamento processado com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao processar pagamento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica o status de um pagamento
     * @param codigoTransacao o código da transação
     * @return o status do pagamento
     */
    public String verificarStatusPagamento(String codigoTransacao) {
        System.out.println("Verificando status do pagamento: " + codigoTransacao);
        return sistemaPagamento.verificarStatus(codigoTransacao);
    }
}
