package Laba6;

import java.util.*;

public class PushDownAutomaton {
    // currentState, inputSymbol, stackSymbol, DataTransaction(backStackList, nextState)
    Map<String, Map<String, Map<String, List<DataTransaction>>>> transactions;
    String startState;

    public PushDownAutomaton(Map<String, Map<String, Map<String, List<DataTransaction>>>> transactions, String startState) {
        this.transactions = transactions;
        this.startState = startState;
    }

    public boolean solve(String inputString) {
        Stack<String> stack = new Stack<>();
        stack.push("Z0"); // Маркер дна
        Queue<StateAutomaton> states = new LinkedList<>();
        states.add(new StateAutomaton(stack, startState, new ArrayList<>(List.of(inputString.split("")))));
        while (!states.isEmpty()) {
            StateAutomaton stateAutomaton = states.poll();
            String currentState = stateAutomaton.state();
            Stack<String> currentStack = stateAutomaton.stack();
            List<String> currentInput = stateAutomaton.input();


            //if (currentInput.isEmpty() && currentStack.size() == 1 && currentStack.pop().equals("Z0")) return true;
            if (currentInput.isEmpty() && currentStack.isEmpty()) return true;
            if (currentStack.isEmpty()) continue;
            if (currentInput.isEmpty() && currentStack.size() > 1) {
                if (!transactions.get(currentState).containsKey("E")) continue;
            };

            Map<String, Map<String, List<DataTransaction>>> currentTransactions = transactions.get(currentState);
            String currentStackSymbol = currentStack.pop();

            // Сначала проверим - есть ли E переходы
            if (currentTransactions.containsKey("E")) {
                List<DataTransaction> dataTransactions = currentTransactions.get("E").get(currentStackSymbol);
                if (dataTransactions != null && !dataTransactions.isEmpty()) {
                    if (currentInput.size() == 1 && currentInput.get(0).isEmpty()) currentInput.remove(0);
                    dataTransactions.forEach(dataTransaction -> states.addAll(processing(currentStack, currentInput, dataTransaction)));
                }
            }

            if (!currentInput.isEmpty()) {
                String inputSymbol = currentInput.remove(0);
                if (currentTransactions.get(inputSymbol) != null) {
                    List<DataTransaction> dataTransactions = currentTransactions.get(inputSymbol).get(currentStackSymbol);
                    if (dataTransactions != null && !dataTransactions.isEmpty()) {
                        dataTransactions.forEach(dataTransaction -> states.addAll(processing(currentStack, currentInput, dataTransaction)));
                    }
                }
            }

        }
        return false;
    }

    private List<StateAutomaton> processing(Stack<String> stack, List<String> input, DataTransaction dataTransaction) {
        List<StateAutomaton> states = new ArrayList<>();
        Stack<String> newStack = new Stack<>();
        newStack.addAll(stack);
        List<String> backStack = new ArrayList<>(dataTransaction.backStack());
        Collections.reverse(backStack); // ревёрсим то, что нужно вернуть в стек
        newStack.addAll(dataTransaction.backStack()); // новый стек = старый стек + новые данные в стек
        dataTransaction.nextStates().forEach(nextState -> {
            states.add(new StateAutomaton(newStack, nextState, new ArrayList<>(input)));  // добаляем новые состояния
        });
        return states;
    }


}
record StateAutomaton(Stack<String> stack, String state, List<String> input) {}