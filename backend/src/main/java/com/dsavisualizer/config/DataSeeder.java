package com.dsavisualizer.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dsavisualizer.model.Problem;
import com.dsavisualizer.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private final ProblemRepository problemRepository;

    @Override
    public void run(String... args) {
        if (problemRepository.count() > 0) {
            return;
        }

        List<Problem> sampleProblems = List.of(
                Problem.builder()
                        .title("Two Sum")
                        .description(
                                "Given an array of integers and a target, return indices of the two numbers such that they add up to the target.")
                        .examples("Input: nums = [2,7,11,15], target = 9\\nOutput: [0,1]")
                        .constraints("2 <= nums.length <= 10^4\\n-10^9 <= nums[i] <= 10^9")
                        .pseudocode(
                                "Use a hash map to store value->index while iterating.\\nFor each num, check if target-num exists.")
                        .difficulty(Problem.Difficulty.EASY)
                        .category(Problem.Category.ARRAYS)
                        .boilerplateCode("public int[] twoSum(int[] nums, int target) {\\n    // TODO\\n}")
                        .solutionCode(
                                "public int[] twoSum(int[] nums, int target) {\\n    java.util.Map<Integer, Integer> map = new java.util.HashMap<>();\\n    for (int i = 0; i < nums.length; i++) {\\n        int need = target - nums[i];\\n        if (map.containsKey(need)) return new int[]{map.get(need), i};\\n        map.put(nums[i], i);\\n    }\\n    return new int[]{};\\n}")
                        .timeLimitMs(3000)
                        .memoryLimitMb(256)
                        .build(),
                Problem.builder()
                        .title("Valid Parentheses")
                        .description(
                                "Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.")
                        .examples("Input: s = \"()[]{}\"\\nOutput: true")
                        .constraints("1 <= s.length <= 10^4")
                        .pseudocode("Use a stack. Push opening brackets, for closing check top pair.")
                        .difficulty(Problem.Difficulty.EASY)
                        .category(Problem.Category.STRINGS)
                        .boilerplateCode("public boolean isValid(String s) {\\n    // TODO\\n}")
                        .solutionCode(
                                "public boolean isValid(String s) {\\n    java.util.Deque<Character> st = new java.util.ArrayDeque<>();\\n    for (char c : s.toCharArray()) {\\n        if (c == '(' || c == '[' || c == '{') st.push(c);\\n        else {\\n            if (st.isEmpty()) return false;\\n            char t = st.pop();\\n            if ((c == ')' && t != '(') || (c == ']' && t != '[') || (c == '}' && t != '{')) return false;\\n        }\\n    }\\n    return st.isEmpty();\\n}")
                        .timeLimitMs(3000)
                        .memoryLimitMb(256)
                        .build(),
                Problem.builder()
                        .title("Number of Islands")
                        .description("Given an m x n 2D binary grid, return the number of islands.")
                        .examples(
                                "Input: grid = [[\"1\",\"1\",\"0\"],[\"0\",\"1\",\"0\"],[\"1\",\"0\",\"1\"]]\\nOutput: 3")
                        .constraints("m, n <= 300")
                        .pseudocode("Traverse grid, run DFS/BFS from each unvisited land cell and count components.")
                        .difficulty(Problem.Difficulty.MEDIUM)
                        .category(Problem.Category.GRAPHS)
                        .boilerplateCode("public int numIslands(char[][] grid) {\\n    // TODO\\n}")
                        .solutionCode(
                                "public int numIslands(char[][] grid) {\\n    int m = grid.length, n = grid[0].length, ans = 0;\\n    for (int i = 0; i < m; i++) {\\n        for (int j = 0; j < n; j++) {\\n            if (grid[i][j] == '1') {\\n                ans++;\\n                flood(grid, i, j);\\n            }\\n        }\\n    }\\n    return ans;\\n}\\nprivate void flood(char[][] g, int r, int c) {\\n    if (r < 0 || c < 0 || r >= g.length || c >= g[0].length || g[r][c] != '1') return;\\n    g[r][c] = '0';\\n    flood(g, r + 1, c);\\n    flood(g, r - 1, c);\\n    flood(g, r, c + 1);\\n    flood(g, r, c - 1);\\n}")
                        .timeLimitMs(5000)
                        .memoryLimitMb(512)
                        .build(),
                Problem.builder()
                        .title("Longest Increasing Subsequence")
                        .description(
                                "Given an integer array nums, return the length of the longest strictly increasing subsequence.")
                        .examples("Input: nums = [10,9,2,5,3,7,101,18]\\nOutput: 4")
                        .constraints("1 <= nums.length <= 2500")
                        .pseudocode("Use DP where dp[i] is LIS ending at i, or binary-search optimization.")
                        .difficulty(Problem.Difficulty.MEDIUM)
                        .category(Problem.Category.DYNAMIC_PROGRAMMING)
                        .boilerplateCode("public int lengthOfLIS(int[] nums) {\\n    // TODO\\n}")
                        .solutionCode(
                                "public int lengthOfLIS(int[] nums) {\\n    int[] tails = new int[nums.length];\\n    int size = 0;\\n    for (int x : nums) {\\n        int i = 0, j = size;\\n        while (i < j) {\\n            int m = (i + j) >>> 1;\\n            if (tails[m] < x) i = m + 1; else j = m;\\n        }\\n        tails[i] = x;\\n        if (i == size) size++;\\n    }\\n    return size;\\n}")
                        .timeLimitMs(5000)
                        .memoryLimitMb(256)
                        .build(),
                Problem.builder()
                        .title("LRU Cache")
                        .description(
                                "Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.")
                        .examples("Input: [\"LRUCache\",\"put\",\"get\"]\\nOutput: [null,null,1]")
                        .constraints("1 <= capacity <= 3000")
                        .pseudocode("Use doubly linked list + hash map for O(1) get and put.")
                        .difficulty(Problem.Difficulty.HARD)
                        .category(Problem.Category.HASHING)
                        .boilerplateCode(
                                "class LRUCache {\\n    public LRUCache(int capacity) {}\\n    public int get(int key) { return -1; }\\n    public void put(int key, int value) {}\\n}")
                        .solutionCode(
                                "class LRUCache {\\n    private final java.util.LinkedHashMap<Integer, Integer> map;\\n    public LRUCache(int capacity) {\\n        map = new java.util.LinkedHashMap<>(capacity, 0.75f, true) {\\n            protected boolean removeEldestEntry(java.util.Map.Entry<Integer, Integer> e) {\\n                return size() > capacity;\\n            }\\n        };\\n    }\\n    public int get(int key) { return map.getOrDefault(key, -1); }\\n    public void put(int key, int value) { map.put(key, value); }\\n}")
                        .timeLimitMs(7000)
                        .memoryLimitMb(512)
                        .build());

        problemRepository.saveAll(sampleProblems);
        log.info("Seeded {} sample problems", sampleProblems.size());
    }
}
