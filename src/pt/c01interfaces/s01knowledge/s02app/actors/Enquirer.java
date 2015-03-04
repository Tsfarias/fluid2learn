package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;

	public Enquirer()
	{
	}


	@Override
	public void connect(IResponder responder)
	{
        int animal = 0;
        boolean animalEsperado = true, achou = false;

        IBaseConhecimento bc = new BaseConhecimento();
        /*Armazena em um array de string a lista de animais*/
        String listaAnimais[] = bc.listaNomes();
        /*Faz do objeto o primeiro animal da string de animais*/

        while(!achou){
            /*Faz do objeto o animal da listaAnimal na posicao animal.
              Ex. 0 = aranha., 1 = camarao...*/
            obj = bc.recuperaObjeto(listaAnimais[animal]);
            IDeclaracao decl = obj.primeira();

            while(decl != null && animalEsperado) {
                /*Pega a pergunta e a resposta já dentro do bd*/
                String pergunta = decl.getPropriedade();
                String respostaEsperada = decl.getValor();

                String resposta = responder.ask(pergunta);
                if (resposta.equalsIgnoreCase(respostaEsperada))
                    decl = obj.proxima();
                else
                    animalEsperado = false;
            }

            /*Caso saiu do loop acima sem ser o animal esperado, ele incrementa
            o contador do animal e faz novamente o loop*/
            if(!animalEsperado){
                animal++;
                animalEsperado = true;
            }else{
                achou = true;
            }
        }

        boolean acertei = responder.finalAnswer(listaAnimais[animal]);

		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
