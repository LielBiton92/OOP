import random
from typing import Dict
from GraphInterface import *
from NodeData import *


class DiGraph(GraphInterface):

    def __init__(self):
        self._nodes: Dict[int, NodeData] = dict()
        self._edges_out: Dict[int, Dict[int, float]] = dict()
        self._edges_in: Dict[int, Dict[int, float]] = dict()
        self.CMC = 0
        self._edgesize = 0

    def v_size(self) -> int:
        return len(self._nodes)

    def e_size(self) -> int:
        return self._edgesize

    def get_all_v(self) -> dict:
        return self._nodes

    def all_in_edges_of_node(self, id1: int) -> dict:
        return self._edges_in[id1]

    def all_out_edges_of_node(self, id1: int) -> dict:
        return self._edges_out[id1]

    def get_mc(self) -> int:
        return self.CMC

    def add_edge(self, id1: int, id2: int, weight: float) -> bool:
        if id1 is not id2 and id1 in self._nodes and id2 in self._nodes and id2 not in self._edges_out.get(id1):
            self._edges_out.get(id1).update({id2: weight})
            self._edges_in.get(id2).update({id1: weight})
            self.CMC += 1
            self._edgesize += 1
            return True
        else:
            return False

    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        if pos is None:
            x = random.random()*1000
            y = random.random()*600
            pos = (x, y, 0)
        node = NodeData(node_id, pos)
        if node_id not in self._nodes:
            self._nodes.update({node_id: node})
            self._edges_out.update({node_id: dict()})
            self._edges_in.update({node_id: dict()})
            self.CMC += 1
            return True
        else:
            return False

    def remove_node(self, node_id: int) -> bool:
        if node_id in self._nodes:
            for edge in self.all_out_edges_of_node(node_id).keys():
                self._edges_in.get(edge).pop(node_id)

                self._edgesize -= 1

            for edge in self.all_in_edges_of_node(node_id):
                self._edges_out.get(edge).pop(node_id)
                self._edgesize -= 1

            self._edges_out.pop(node_id)
            self._edges_in.pop(node_id)
            self._nodes.pop(node_id)
            self.CMC += 1
            return True
        else:
            return False

    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        if node_id1 is not node_id2 and node_id1 in self._nodes and node_id2 in self._nodes and node_id2 in self._edges_out.get(
                node_id1):
            self._edges_out.get(node_id1).pop(node_id2)
            self._edges_in.get(node_id2).pop(node_id1)
            self.CMC += 1
            self._edgesize -= 1
            return True
        else:
            return False

    @property
    def nodes(self):
        return self._nodes

    def getnode(self, key: int):
        if key in self._nodes:
            return self._nodes.get(key)
        return None

    def __str__(self):
        return f"Graph: |V|={self.v_size()} |E|={self.e_size()}"

    def __repr__(self):
        return f"Graph: |V|=4{self.v_size()} |E|={self.e_size()}"

    # def __str__(self):
    #     return f"Graph: |V|={self.v_size()} |E|:{self.e_size()} \n,outedges: {self._edges_out}\n inedges: {self._edges_in}"
    #
    # def __repr__(self):
    #     return f"Graph: |V|=4{self.v_size()} |E|: {self.e_size()}\n ,outedges: {self._edges_out}\n inedges: {self._edges_in}"
