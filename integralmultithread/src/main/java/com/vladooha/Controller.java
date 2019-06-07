package com.vladooha;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {
    private class IntegralDataContainer {
        private String function;
        private Double leftBound;
        private Double rightBound;
        private Integer threadCount;

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public Double getLeftBound() {
            return leftBound;
        }

        public void setLeftBound(Double leftBound) {
            this.leftBound = leftBound;
        }

        public Double getRightBound() {
            return rightBound;
        }

        public void setRightBound(Double rightBound) {
            this.rightBound = rightBound;
        }

        public Integer getThreadCount() {
            return threadCount;
        }

        public void setThreadCount(Integer threadCount) {
            this.threadCount = threadCount;
        }
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        IntegralDataContainer integralDataContainer = controller.getInputData();
        long begin = System.nanoTime();
        Double result = controller.calcIntegral(integralDataContainer);
        long end = System.nanoTime();

        if (result != null) {
            System.out.println(
                    String.format(
                            "Integral: %.4f. Execution time: %d ns",
                            result,
                            end - begin));
        } else {
            System.err.println("Error occurred during calculation!");
        }
    }

    public IntegralDataContainer getInputData() {
        Scanner scanner = new Scanner(System.in);
        IntegralDataContainer data = new IntegralDataContainer();

        try {
            while (data.getFunction() == null) {
                System.out.println("Enter function [only 1 'x' argue]: ");
                String function = scanner.nextLine();

                if (function.matches("^[\\^+\\-/*x() 0-9]+$")) {
                    data.setFunction(function);
                } else {
                    System.err.println("Incorrect input, try again!");
                }
            }

            System.out.println("Enter x left bound [double]: ");
            data.setLeftBound(scanner.nextDouble());

            System.out.println("Enter x right bound [double]: ");
            data.setRightBound(scanner.nextDouble());

            System.out.println("Enter thread count [int]: ");
            data.setThreadCount(scanner.nextInt());

            return data;
        } catch (InputMismatchException e) {
            System.err.println("Incorrect input, try again!");

            return getInputData();
        }
    }

    public Double calcIntegral(IntegralDataContainer data) {
        if (data == null) {
            System.err.println("Empty data!");

            return null;
        }

        String function = data.getFunction();
        Double leftBound = data.getLeftBound();
        Double rightBound = data.getRightBound();
        Integer threadCount = data.getThreadCount();

        if (function == null || leftBound == null || rightBound == null || threadCount == null) {
            System.err.println("Empty data fields!");

            return null;
        }


        double threadCountDbl = (double) threadCount;
        double partLength = (rightBound - leftBound) / threadCountDbl;

        List<Thread> threads = new ArrayList<>();
        List<Double> threadResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < threadCount; ++i) {
            double partLeftBound = leftBound + (double) i * partLength;
            double partRightBound = partLeftBound + partLength;
            String answer = String.format("Thread #%d result: ", i);

            threads.add(new Thread(() -> {
                MathCounter mathCounter = new MathCounter();

                double result = mathCounter.calcIntegral(function, partLeftBound, partRightBound);
                threadResults.add(result);
                System.out.println(
                        String.format(
                                "%s %.8f [from %.4f to %.4f]",
                                answer,
                                result,
                                partLeftBound,
                                partRightBound));
            }));
        }

        try {
            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            double result = 0.0;
            for (Double singleResult : threadResults) {
                if (singleResult != null) {
                    result += singleResult;
                }
            }

            return result;
        } catch (InterruptedException e) {
            System.err.println("Exception during calculation");
            e.printStackTrace();

            return null;
        }
    }
}
