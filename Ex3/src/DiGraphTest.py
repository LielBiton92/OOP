import unittest
from DiGraph import *

g = DiGraph()
g.add_node(1)
g.add_node(2)
g.add_node(3)
g.add_node(4)
g.add_node(5)
g.add_edge(1, 2, 5)
g.add_edge(2, 3, 5)
g.add_edge(3, 4, 5)
g.add_edge(4, 5, 5)
g.add_edge(5, 1, 5)


class GraphAlgoTest(unittest.TestCase):




    def test_v_size(self):
        self.assertEqual(g.v_size(),5)

    def test_e_size(self):
        self.assertEqual(g.e_size(), 5)

    def test_get_all_v(self):
        dict = g.get_all_v()
        self.assertEqual(g.get_all_v(), dict )

    def test_all_out_edges_of_node(self):
      #  print(g.all_out_edges_of_node(1))
        self.assertEqual(g.all_out_edges_of_node(1), {2: 5})
        self.assertEqual(g.all_out_edges_of_node(2), {3: 5})


    def test_all_in_edges_of_node(self):
         self.assertEqual(g.all_in_edges_of_node(1), {5: 5})
         self.assertEqual(g.all_in_edges_of_node(2), {1: 5})

    def test_add_edge(self):
        g.add_edge(1,3,2)

        self.assertEqual(g.all_out_edges_of_node(1), {2: 5, 3: 2} )

    def test_add_node(self):
        g.add_node(6)
        self.assertEqual(g.v_size(), 6)
