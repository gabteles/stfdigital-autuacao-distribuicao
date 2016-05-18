import distribuicao from "./distribuicao.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;

export class TipoDistribuicao {
    public id: string;
    public descricao: string;
    
    constructor(id: string, descricao: string) {
        this.id = id;
        this.descricao = descricao;
    }
}

export class Ministro {
    public id: number;
    public nome: string;
    
    constructor(id: number, nome: string){
        this.id = id;
        this.nome = nome;
    }
}

export class Processo {
    public id: number;
    public numero: string;
    public partes: Array<string>;
    
    constructor(id: number, numero: string, partes: Array<string>) {
        this.id = id;
        this.numero = numero;
        this.partes = partes;
    }
}

/* Comando usado para enviar um processo para distribuição. */
class DistribuirProcessoCommand {
    /* Identificador da distribuição. */
    public distribuicaoId: number;
    
    /* Identificador do processo que será distribuído. */
    public processoId: number;
    
    /* Tipo da distribuição. */
    public tipoDistribuicao: string;
    
    /* Justificativa da distribuição. */
    public justificativa: string;
    
    /* Lista dos ministros candidatos à relatoria. */
	public ministrosCandidatos: Array<number>;
	
	/* Lista dos ministros impedidos de relatar o processo. */
	public ministrosImpedidos: Array<number>;
	
	/* Lista dos processos que embasam a prevenção. */
	public processosPreventos: Array<number>;
    
    constructor(distribuicaoId: number, processoId: number, tipoDistribuicao: string, justificativa: string, ministrosCandidatos: Array<number>, 
        ministrosImpedidos: Array<number>, processosPreventos: Array<number>) {
        this.distribuicaoId = distribuicaoId;
        this.processoId = processoId;
        this.tipoDistribuicao = tipoDistribuicao;
        this.justificativa = justificativa;
        this.ministrosCandidatos = ministrosCandidatos;
        this.ministrosImpedidos = ministrosImpedidos;
        this.processosPreventos = processosPreventos;
    }
}

export class DistribuicaoService {
    
    //Endereço do serviço para salvar um documento temporário.
    private static urlServicoDistribuicao: string = "/distribuicao/api/distribuicao";
    private static urlServicoMinistro: string = "/distribuicao/api/ministros";

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties) { 
        
    }
    
    public listarTiposDistribuicao(): Array<TipoDistribuicao> {
        let tipos = new Array<TipoDistribuicao>();
        tipos.push(new TipoDistribuicao("COMUM", "Comum"));
        tipos.push(new TipoDistribuicao("PREVENCAO", "Prevenção Relator/Sucessor"));
        
        return tipos;
    }
    
    public listarMinistros(): IPromise<Ministro[]> {
        return this.$http.get(this.properties.url + ":" + this.properties.port + DistribuicaoService.urlServicoMinistro)
            .then((response: IHttpPromiseCallbackArg<Ministro[]>) => { 
                return response.data;
            });
        
        /*
        let ministros: Array<Ministro> = new Array<Ministro>();
        
        ministros.push(new Ministro(1, "Min. Presidente"));
        ministros.push(new Ministro(28, "Min. Celso de Mello"));
        ministros.push(new Ministro(30, "Min. Marco Aurélio"));
        ministros.push(new Ministro(36, "Min. Gilmar Mendes"));
        ministros.push(new Ministro(42, "Min. Cármen Lúcia"));
        ministros.push(new Ministro(44, "Min. Dias Toffoli"));
        ministros.push(new Ministro(45, "Min. Luiz Fux"));
        ministros.push(new Ministro(46, "Min. Rosa Weber"));
        ministros.push(new Ministro(47, "Min. Teori Zavascki"));
        ministros.push(new Ministro(48, "Min. Roberto Barroso"));
        ministros.push(new Ministro(49, "Min. Edson Fachin"));
        
        return ministros;*/
    }
    
    public consultarProcessoPrevencao(numero: string): Processo {        
        let processo = new Processo(0, "", []);
        
        if (numero == "ADI 123/2016") {
            processo.id = 5800;
            processo.numero = numero; 
        } else if (numero == "ADI 456/2016") {
            processo.id = 5969;
            processo.numero = numero; 
        }
        
        return processo;
    }
    
    public consultarProcessoParaDistribuicao(numero: number): Processo {        
        let partes = new Array<string>();
        partes.push("FULANO KAMEHAMEHA");
        partes.push("ISAMU MINAMI");
        
        return new Processo(1, "ADI 999/2016", partes);
    }
    
    public enviarProcessoParaDistribuicao(processoId: number, distribuicaoId: number, tipo: string, justificativa: string, minCandidatos: Array<Ministro>,
        minImpedidos: Array<Ministro>, procPreventos: Array<Processo>): IPromise<any> {
        let ministrosCandidatos = [];
        let ministrosImpedidos = [];
        let processosPreventos = [];
        
        for (let i = 0; i < minCandidatos.length; i++){
            ministrosCandidatos.push(minCandidatos[i].id);
        }
                
        for (let j = 0; j < minImpedidos.length; j++){
            ministrosImpedidos.push(minImpedidos[j].id);    
        }
        
        for (let k = 0; k < procPreventos.length; k++) {
            processosPreventos.push(procPreventos[k].id);
        }
                
        let distribuirProcessoCommand = new DistribuirProcessoCommand(distribuicaoId, processoId, tipo, justificativa, ministrosCandidatos, 
            ministrosImpedidos, processosPreventos);
        
        return this.$http.post(this.properties.url + ":" + this.properties.port + DistribuicaoService.urlServicoDistribuicao, distribuirProcessoCommand);
    }
}

distribuicao.service('app.novo-processo.distribuicao.DistribuicaoService', DistribuicaoService);

export default distribuicao;