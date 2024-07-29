package dev.alexcoss.reportgenerator.service;

import dev.alexcoss.reportgenerator.dao.DataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    @Value("${filePath.report}")
    private Path path;
    @Value("${threads.count}")
    private int threadCount;
    @Value("${batch.size}")
    private int batchSize;

    private final DataFetcher dataFetcher;
    private final ExcelWriter excelWriter;

    public void processAndWriteData() {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            int totalRecords = dataFetcher.fetchTotalRecords();
            int totalBatches = (int) Math.ceil((double) totalRecords / batchSize);

            submitTasks(executor, totalBatches);
            shutdownExecutor(executor);
            saveToFile();
        } catch (Exception e) {
            log.error("Error processing data", e);
        } finally {
            executor.shutdownNow();
        }
    }

    private void submitTasks(ExecutorService executor, int totalBatches) {
        for (int i = 0; i < totalBatches; i++) {
            int offset = i * batchSize;
            executor.submit(() -> {
                try {
                    var batch = dataFetcher.fetchData(offset, batchSize);
                    excelWriter.writeBatch(batch);
                } catch (Exception e) {
                    log.error("Error processing batch", e);
                }
            });
        }
    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60L, TimeUnit.MINUTES)) {
                log.warn("Executor did not terminate in the specified time.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Thread was interrupted during shutdown", e);
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
    }

    private void saveToFile() {
        try {
            excelWriter.saveToFile(path);
        } catch (IOException e) {
            log.error("Error saving to file", e);
        }
    }
}
