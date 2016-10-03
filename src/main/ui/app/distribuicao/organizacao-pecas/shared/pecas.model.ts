import Command = app.support.command.Command;

export const API_PECAS = '/distribuicao/api/organizacao-pecas';

export class TipoPeca {
    constructor(public tipoId: number, public nome: string) {}
}

export class PecaSelecionavel {
    constructor(public processoId: number, public peca: Peca) {}
}

export class Visibilidade {
    constructor(public id: string, public nome : string) {}
}

export class TipoAnexo {
    public id: number;
    public nome: string;
    
    constructor(id: number, nome: string) {
        this.id = id;
        this.nome = nome;   
    }
}

export class DocumentoTemporario {
    public arquivo: any;
    public documentoTemporario : any;
    public tipo: TipoPeca;
    public isUploadConcluido: boolean;
    
    constructor(arquivo: any, documentoTemporario: any, tipo: TipoPeca, isUploadConcluido) {
        this.arquivo = arquivo;
        this.documentoTemporario = documentoTemporario;
        this.tipo = tipo;
        this.isUploadConcluido = isUploadConcluido;
    }
}

export class DeleteTemporarioCommand {
    public files: Array<string>;

    constructor(files: Array<string>) {
        this.files = files;
    }   
}

export class DocumentoTemporarioDto {
    public tipoDocumentoId: number;
    public documentoId: string;
    
    constructor(tipoDocumentoId: number, documentoId: string){
        this.tipoDocumentoId = tipoDocumentoId;
        this.documentoId = documentoId;
    }
}

export class Documento {
    constructor(public id: number, public numeroConteudo : string, 
          public tamanho: number, public quantidadePaginas : number){}
}

export class OrganizaPecasCommand  implements Command {
    public distribuicaoId: number;
    public pecas: Array<Number>;
    public finalizarTarefa: boolean;

    constructor() {}
}

export class Peca {
    public pecaId: number;
    public documentoId: number;
    public tipoPeca: number;
    public descricao: string;
    public numeroOrdem: number;
    public visibilidade: string;
    public situacao: string;

    constructor() {}
}


export class PecasCommand implements Command{
    public processoId : number;
    public pecas : Array<number> = [];
    
    constructor(){}
}

export class ExcluirPecasCommand extends PecasCommand{
    constructor(){
        super();
    }
}

export class UnirPecasCommand extends PecasCommand{
    constructor(){
        super();
    }
}

export class JuntarPecaCommand extends PecasCommand{
    constructor(){
        super();
    }
}

export class InserirPecaCommand implements Command {
    public processoId : number;
    public pecas : Array<CadastrarPecaCommand> = [];
    
    constructor(processoId : number) {
        this.processoId = processoId;
    }
}

export class CadastrarPecaCommand {
    public documentoTemporarioId : string;
    public tipoPecaId : number;
    public numeroOrdem : number;

    constructor(documentoTemporarioId : string, tipoPecaId : number, numeroOrdem : number){
        this.documentoTemporarioId = documentoTemporarioId;
        this.tipoPecaId = tipoPecaId;
        this.numeroOrdem = numeroOrdem;
    };
}


export class DividirPecaCommand implements Command {
    public processoId : number;
    public pecaOriginalId : number;
    public pecas : Array<QuebrarPecaCommand> = [];
    
    constructor(pecaOriginalId : number, processoId : number){
        this.pecaOriginalId = pecaOriginalId;
        this.processoId = processoId;
    }
}

export class QuebrarPecaCommand {
    public documentoTemporarioId : string;
    public tipoPecaId : number;
    public visibilidade : string;
    public situacao : string;
    public descricao : string;
    public paginaInicial : number;
    public paginaFinal : number;

    constructor(situacao : string, documentoTemporarioId ?: string, tipoPecaId? : number, visibilidade?: string, descricao?: string, 
            paginaInicial?: number, paginaFinal?: number){
        this.situacao = situacao;
        this.documentoTemporarioId = documentoTemporarioId;
        this.tipoPecaId = tipoPecaId;
        this.visibilidade = visibilidade;
        this.situacao = situacao;
        this.descricao = descricao;
        this.paginaInicial = paginaInicial;
        this.paginaFinal = paginaFinal;
    }
}

export class EditarPecaCommand implements Command{
    public processoId : number;
    public pecaId : number;
    public tipoPecaId : number;
    public descricao : string;
    public numeroOrdem : number;
    public visibilidade : string;
    
    constructor(peca: Peca, processoId: number){
       this.pecaId = peca.pecaId;
       this.descricao = peca.descricao;
       this.tipoPecaId = peca.tipoPeca;
       this.visibilidade = peca.visibilidade;
       this.processoId = processoId;
       this.numeroOrdem = peca.numeroOrdem;
    };
    
}


export interface Processo {
    processoId: number;
    relator: number;
    pecas: Array<Peca>;
}