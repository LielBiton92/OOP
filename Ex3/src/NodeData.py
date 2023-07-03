class NodeData(object):

    def __init__(self, key: int, pos: tuple):
        self._key = key
        self.pos = pos
        self.tag = 1
        # white - 1
        # gray - 0
        # black = -1
        self.visited = 1
        # 1 - yes
        # 0 - no
        self.info = ""
        self._weight = 0

    def getKey(self) -> int:
        return self._key

    def getpos(self):
        return self.pos

    def setpos(self, pos: tuple):
        self.pos = pos
        return self.pos

    def __str__(self):
        return f"id : {self._key} pos{self.pos} weight:{self._weight} "

    def __repr__(self):
        return f"id : {self._key} pos{self.pos} weight: {self._weight}"

    def setinfo(self, t: str):
        self.info = t
        return self.info

    def getinfo(self):
        return self.info

    def setweight(self, t: int):
        self._weight = t
        return self._weight

    def getweight(self):
        return self._weight

    def settag(self, tag):
        self.tag = tag

    def gettag(self):
        return self.tag

    def getcolor(self):
        return self.color

    def setcolor(self, color):
        self.color = color


