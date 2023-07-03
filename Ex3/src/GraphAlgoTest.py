import unittest
from GraphAlgo import *


class GraphAlgoTest(unittest.TestCase):

    def test_center10000(self):
        graph = GraphAlgo()
        graph.load_from_json("../data/10000Nodes.json")
        self.assertEqual(graph.centerPoint(), (3846, 1832.1504410230123))
        print("center-10000Nodes")

    def test_center1000Nodes(self):
        graph = GraphAlgo()
        graph.load_from_json("../data/1000Nodes.json")
        self.assertEqual(graph.centerPoint(), (362, 1185.9594924690523))
        print("center1000Nodes")

    def test_centers(self):
        g_algo = GraphAlgo()
        file = '../data/A0.json'
        file1 = '../data/A1.json'
        file2 = '../data/A2.json'
        file3 = '../data/A3.json'
        file4 = '../data/A4.json'
        file5 = '../data/A5.json'

        g_algo.load_from_json(file)
        self.assertEqual(g_algo.centerPoint(), (7, 6.806805834715163))
        g_algo.load_from_json(file1)
        self.assertEqual(g_algo.centerPoint(), (8, 9.925289024973141))
        g_algo.load_from_json(file2)
        self.assertEqual(g_algo.centerPoint(), (0, 7.819910602212574))
        g_algo.load_from_json(file3)
        self.assertEqual(g_algo.centerPoint(), (2, 8.182236568942237))
        g_algo.load_from_json(file4)
        self.assertEqual(g_algo.centerPoint(), (6, 8.071366078651435))
        g_algo.load_from_json(file5)
        self.assertEqual(g_algo.centerPoint(), (40, 9.291743173960954))

    def test_shortestpath(self):
        file = '../data/1000Nodes.json'
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertEqual(g.shortest_path(60, 600), (699.0455128494779, [60, 540, 103, 362, 30, 935, 645, 10, 160, 600]))
        #   self.assertEqual(g.shortest_path(121, 711), (1032.1449144932362, [121, 903, 867, 173, 505, 711]))
        print("shortest 1000Nodes")

    def test_tspA5(self):
        file = '../data/A5.json'
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertEqual(g.TSP([1, 2, 3]), ([1, 9, 2, 3], 2.370613295323088))
        print("A5 TSP")

    def test_tsp1000Nodes(self):
        file = '../data/1000Nodes.json'
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertEqual(g.TSP([1, 2, 3]), ([1, 498, 685, 454, 656, 888, 857, 940, 2, 185, 977, 791, 236, 3],
                                            1511.5899351120368))
        print("TSP-1000Nodes")

    def test_tsp10000Nodes(self):
        file = '../data/10000Nodes.json'
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertEqual(g.TSP([1, 2, 3]), (
        [1, 3990, 9861, 3226, 9034, 6701, 3961, 6329, 4153, 9651, 1265, 2, 8508, 5916, 5478, 5998, 9857, 8335, 3],
        1954.319057017678))
        print("TSP-10000Nodes")

    def test_shortestpath10000Nodes(self):
        file = '../data/10000Nodes.json'
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertEqual(g.shortest_path(600, 6000), (1307.1651310329542,
                                                      [600, 1989, 6438, 4902, 6216, 4282, 7476, 698, 3344, 769, 6350,
                                                       6000]))
        print("shortestpath1000Nodes")

    def test_load1000Nodes(self):
        file = "../data/1000Nodes.json"
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertTrue(g.load_from_json(file))
        self.assertEqual(g.graph.v_size(), 1000)
        # self.assertEqual(g.graph.e_size(), 166)
        print("LOAD 1000NODES")

    def test_load10000(self):
        file = "../data/10000Nodes.json"
        g = GraphAlgo()
        g.load_from_json(file)
        self.assertTrue(g.load_from_json(file))
        self.assertEqual(g.graph.v_size(), 10000)
        self.assertEqual(g.graph.e_size(), 90000)
        print("load10000Nodes")

    def test_saveA5(self):
        file = "../data/1000Nodes.json"
        g = GraphAlgo()
        g.load_from_json(file)
        g.graph.add_node(1001, "172.0,925.0,981.0")
        g.save_to_json("test_save.json")
        g.load_from_json("../data/test_save.json")
        self.assertEqual(g.graph.v_size(), 1001)
        print("SAVE 1000Nodes")

    def test_save10000(self):
        file = "../data/10000Nodes.json"
        g = GraphAlgo()
        g.load_from_json(file)
        g.graph.add_node(10005, "172.0,925.0,981.0")
        g.save_to_json("test_save.json")
        g.load_from_json("../data/test_save2.json")
        self.assertEqual(g.graph.v_size(), 10001)
        print("testSave-10000Nodes")
