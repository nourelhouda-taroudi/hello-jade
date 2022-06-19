package devoir.ensa;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class Agent2 extends Agent {
    boolean end=false;

    @Override
    protected void setup() {
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

        System.out.println("Je suis Agent " + this.getName());
        String mess = "Bonjour,je suis agent2 je vais initier !";
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("agent1", AID.ISLOCALNAME));
        msg.setContent(mess);
        System.out.println("Moi >>> agent1 " + mess);
        this.send(msg);

        this.addBehaviour(new SimpleBehaviour() {


            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg == null) this.block();
                else {
                    if (msg.equals("EOS")) end=true;
                    System.out.println("Moi Y <<< " + msg.getSender().getLocalName() + " : " + msg.getContent());

                }
            }

            @Override
            public boolean done() {
                return end;
            }
        });
    }
    }

