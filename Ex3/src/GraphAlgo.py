import heapq
import json
import math


import pygame
from typing import List


from DiGraph import *
from GraphAlgoInterface import GraphAlgoInterface
from GraphInterface import GraphInterface
from NodeData import NodeData

rad = math.pi / 180


class GraphAlgo(GraphAlgoInterface):

    def __init__(self, g: DiGraph = DiGraph()):
        self.graph = g

    def get_graph(self) -> GraphInterface:
        return self.graph

    def load_from_json(self, file_name: str) -> bool:
        try:
            with open(file_name) as f:
                temp = json.load(f)

            graph = DiGraph()
            for node in temp["Nodes"]:
                try:
                    pos = tuple(float(x) for x in node["pos"].split(","))
                except:
                    pos = None
                graph.add_node(node["id"], pos)

            for edge in temp["Edges"]:
                graph.add_edge(edge["src"], edge["dest"], edge["w"])

            self.graph = graph
            return True

        except Exception as ex:
            return False

    def save_to_json(self, file_name: str) -> bool:

        Nodes = []
        Edges = []
        for Node in self.graph._nodes.values():
            id = Node.getKey()
            edgesout = self.graph.all_out_edges_of_node(Node.getKey())

            Nodes.append({'pos': Node.getpos(), 'id': id})
            for d in edgesout:
                Edges.append({'src': id, 'w': edgesout[d], 'dest': d})

        lists = {'Edges': Edges, 'Nodes': Nodes}

        try:
            with open(file_name, 'w') as f:
                json.dump(lists, f, indent=2)
        except FileNotFoundError:
            return False
        return True

    def shortest_path(self, id1: int, id2: int) -> (float, list):
        if self.graph.getnode(id1) == None or self.graph.getnode(id2) == None:
            return math.inf, []

        return self.dijkstra(id1, id2)

        pass

    def centerPoint(self) -> (int, float):
        if self.isconnected() == False:
            return None, float(math.inf)

        # positive_infinity = float('inf')
        ans = None
        shortestdist = math.inf
        for Node in self.graph.get_all_v().values():
            # self.dijkstra2(Node.getKey())
            max_dist = -1
            # for Node2 in self.graph.get_all_v().values():
            for Node2 in self.dijkstra2(Node.getKey()).values():
                if Node2 > max_dist:
                    max_dist = Node2
            if max_dist < shortestdist:
                shortestdist = max_dist
                ans = Node

        return ans.getKey(), shortestdist

    def runFirstIteration(self, targets: List[NodeData]):
        currnetDist = math.inf
        minPath = math.inf
        vertex = 0
        for i in targets:
            for j in targets:
                currnetDist = self.shortest_path(targets[i].getKey(), targets[j].getKey())
                if currnetDist < minPath and currnetDist != 0:
                    minPath = currnetDist
                    vertex = targets[i].getKey()
        return vertex

    def TSP(self, node_lst: List[int]) -> (List[int], float):

        if len(node_lst) <= 1:
            return ([node_lst, 0])
        path = []
        dist = 0
        for i in range(0, len(node_lst) - 1):
            node1 = node_lst[i]
            node2 = node_lst[i + 1]
            k = (self.shortest_path(node1, node2)[1])
            dist = dist + self.shortest_path(node1, node2)[0]
            for n in k:
                if not path.__contains__(n):
                    path.append(n)

        return path, dist

    def plot_graph(self) -> None:
        WIDTH, HEIGHT = 1000, 600
        WIN = pygame.display.set_mode((WIDTH, HEIGHT))
        WIN.fill((255, 255, 255))
        pygame.font.init()
        self.drawgraph(WIN)

        run = True
        while run:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    run = False
        pygame.quit()

    def drawgraph(self, WIN: pygame.Surface):
        self.paintNodes(WIN)
        pygame.display.update()

    def paintNodes(self, WIN: pygame.Surface):
        points = self.getmaxandminxy()  # return max_x,max_y,min_x,min_y
        for node in self.graph.get_all_v().values():
            x, y = self.getscaledxy(WIN, points, node.getpos())
            pygame.draw.circle(WIN, (255, 0, 0), (x, y), 5)
            pygame.init()
            font = pygame.font.SysFont('arial', 15)
            key = f"{node.getKey()}"
            txt = font.render(key, True, (255, 0, 0))
            WIN.blit(txt, (x - 20, y - 20))

            for edge in self.graph.all_out_edges_of_node(node.getKey()):
                x_dest, y_dest = self.getscaledxy(WIN, points, self.graph.getnode(edge).getpos())
                self.draw_arrow_head(WIN, x, y, x_dest, y_dest)


    def getscaledxy(self, WIN: pygame.Surface, points: tuple, pos: tuple):
        WIDTHFACTOR = WIN.get_width() / (points[0] - points[2])
        HEIGHTFACTOR = WIN.get_height() / (points[1] - points[3])
        x = (((pos[0] - points[2]) * WIDTHFACTOR)) * 0.65 + 155
        y = (((pos[1] - points[3]) * HEIGHTFACTOR)) * 0.65 + 83

        return x, y

    def text(surface, fontFace, size, x, y, text, colour):
        font = pygame.font.Font(fontFace, size)
        text = font.render(text, 1, colour)
        surface.blit(text, (x, y))

    def draw_arrow_head(self, win: pygame.surface, x1: float, y1: float, x2: float, y2: float) -> None:
        # geek4geeks
        arrow_width = 15
        arrow_height = 2
        diff_x = x2 - x1
        diff_y = y2 - y1
        D = math.sqrt(diff_x * diff_x + diff_y * diff_y)
        xm = D - arrow_width
        xn = xm
        ym = arrow_height
        yn = -arrow_height

        sin = diff_y / D
        cos = diff_x / D

        x = xm * cos - ym * sin + x1
        ym = xm * sin + ym * cos + y1
        xm = x

        x = xn * cos - yn * sin + x1
        yn = xn * sin + yn * cos + y1
        xn = x

        points = ((x2, y2), (xm, ym), (xn, yn))

        pygame.draw.line(win, (0, 0, 0), (x1, y1), (x2, y2))
        pygame.draw.polygon(win, (0, 0, 0), points)

    def getmaxandminxy(self):
        max_x = -1
        min_x = math.inf
        max_y = -1
        min_y = math.inf
        for node in self.graph.get_all_v().values():
            if node.getpos() != None:
                if node.getpos()[0] < min_x:
                    min_x = node.getpos()[0]
                if node.getpos()[1] < min_y:
                    min_y = node.getpos()[1]
                if node.getpos()[0] > max_x:
                    max_x = node.getpos()[0]
                if node.getpos()[1] > max_y:
                    max_y = node.getpos()[1]

        return max_x, max_y, min_x, min_y

    def bfs(self, n1: NodeData):
        for node in self.graph.get_all_v().values():
            node.setweight(math.inf)
            node.settag(1)
        n1.settag(0)
        n1.setweight(0)
        queue = [n1]
        while len(queue) > 0:
            u = queue.pop(0)
            for check in self.graph.all_out_edges_of_node(u.getKey()).keys():
                v = self.graph.get_all_v().get(check)
                if v.gettag() == 1:
                    v.settag(0)
                    v.setweight(u.getweight() + 1)
                    queue.append(v)
            u.settag(-1)


    def isconnected(self):
        u = next(iter(self.graph.get_all_v().values()))
        self.bfs(u)
        for n2 in self.graph.get_all_v().values():
            if n2.gettag() != -1:
                return False
        self.reverse()
        for n4 in self.graph.get_all_v().values():
            n4.visited = 1
        self.bfs(u)
        self.reverse()
        for n3 in self.graph.get_all_v().values():
            if n3.gettag() != -1:
                return False
        return True

    def reverse(self):
        temp = self.graph._edges_out.copy()
        self.graph._edges_out = self.graph._edges_in
        self.graph._edges_in = temp

    def dijkstra(self, src: int, dest: int) -> (float, list):
        prev = {src: -1}
        dist = {i: math.inf for i in self.graph.get_all_v().keys()}

        dist[src] = 0
        q = []
        heapq.heappush(q, (0, src))
        while q:
            v = heapq.heappop(q)[1]
            for edge, edge2 in self.graph.all_out_edges_of_node(v).items():
                if dist[edge] > dist[v] + edge2:
                    dist[edge] = dist[v] + edge2
                    prev[edge] = v
                    heapq.heappush(q, (dist[edge], edge))

            if v == dest:
                break

        if dist[dest] == math.inf:
            return float('inf'), []
        path = []
        p = dest

        while p != -1:
            path.insert(0, p)
            p = prev[p]
        return dist[dest], path

    def dijkstra2(self, src: int) -> (float, list):
        visited = set()
        dist = {i: math.inf for i in self.graph.get_all_v().keys()}
        dist[src] = 0
        q = []
        heapq.heappush(q, (0, src))
        while len(q) > 0:
            v = heapq.heappop(q)[1]
            if not visited.__contains__(v):
                visited.add(v)
                for edge, edge2 in self.graph.all_out_edges_of_node(v).items():
                    temp = self.graph.getnode(edge)
                    if not visited.__contains__(temp.getKey()):
                        if dist[edge] > dist[v] + edge2:
                            dist[edge] = dist[v] + edge2
                            self.graph.getnode(edge).setweight(dist[edge])
                            heapq.heappush(q, (dist[edge], edge))
        return dist

    def dijkstra(self, src: int, dest: int) -> (float, list):
        prev = {src: -1}
        dist = {i: math.inf for i in self.graph.get_all_v().keys()}

        dist[src] = 0
        q = []
        heapq.heappush(q, (0, src))
        while q:
            v = heapq.heappop(q)[1]
            for edge, edge2 in self.graph.all_out_edges_of_node(v).items():
                if dist[edge] > dist[v] + edge2:
                    dist[edge] = dist[v] + edge2
                    prev[edge] = v
                    heapq.heappush(q, (dist[edge], edge))

            if v == dest:
                break

        if dist[dest] == math.inf:
            return float('inf'), []
        path = []
        p = dest

        while p != -1:
            path.insert(0, p)
            p = prev[p]
        return dist[dest], path
