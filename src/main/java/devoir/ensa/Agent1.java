package devoir.ensa;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Scanner;

public class Agent1 extends Agent {


    @Override
    protected void setup(){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Chat-Ensa");
        sd.setName(getLocalName()+"-Chat-Ensa");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
            System.out.println("L'agent : "+getAID().getLocalName()+" est enregistré dans DF");
        }   catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("je suis Agent :"+this.getName());
        this.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg=myAgent.receive();
                if(msg==null) this.block();
                else {
                    System.out.println("Moi <<< "+msg.getSender().getLocalName()+" : "+msg.getContent());
                    myAgent.addBehaviour(new CyclicBehaviour() {
                        String mess;
                        @Override
                        public void action() {
                            Scanner scanner=new Scanner(System.in);
                            System.out.println("Entrez le message que vous voullez à l Agent 2");
                            mess=scanner.nextLine();
                            if (msg.equals("EOS")) this.done();
                            ACLMessage msg2=new ACLMessage(ACLMessage.INFORM);
                            msg2.addReceiver(msg.getSender());
                            msg2.setContent(mess);
                            System.out.println("Moi agent1 >>> "+msg.getSender().getLocalName()+" : "+mess);
                            myAgent.send((msg2));
                            myAgent.doWait(2000);
                        }
                    });
                }
            }
        });
    }
}
