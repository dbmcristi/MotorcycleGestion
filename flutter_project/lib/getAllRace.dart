import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(home: JsonApiPhp()));
}

class JsonApiPhp extends StatefulWidget {
  @override
  _JsonApiPhpState createState() => _JsonApiPhpState();
}

class _JsonApiPhpState extends State<JsonApiPhp> {
  bool loading = true;
  final TextEditingController _name = TextEditingController();
  final TextEditingController _engine = TextEditingController();
  final TextEditingController _id = TextEditingController();

  // var client = http.Client();
  List<RaceDto> races = [];

  @override
  void initState() {
    fetchData();
    super.initState();
  }

  void fetchData() async {
    var response = await http.get(Uri.http('localhost:8084', '/race/all'));
    if (response.statusCode == 200) {
      // Connection Ok
      races = [];
      List responseJson = json.decode(response.body);
      responseJson.map((m) => races.add(RaceDto.fromJson(m))).toList();
      print("fetchData ");
      print(races);
    }
  }

  Future<RaceDto> save(String id, String name, String engineCapacity) async {
    http.Response response;
    if (id == "") {
      response = await http.post(Uri.http('localhost:8084', '/race/'),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
          },
          body: jsonEncode(<String, String>{
            "name": name,
            "engineCapacity": engineCapacity,
          }));
    } else {
      response = await http.put(Uri.http('localhost:8084', '/race/'),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
          },
          body: jsonEncode(<String, String>{
            "id": id,
            "name": name,
            "engineCapacity": engineCapacity,
          }));
    }

    if (response.statusCode == 200) {
      // If the server did return a 200 CREATED response,
      // then parse the JSON.
      return RaceDto.fromJson(
          jsonDecode(response.body) as Map<String, dynamic>);
    } else {
      // If the server did not return a 200 CREATED response,
      // then throw an exception.
      throw Exception('Failed to save Race.' + response.statusCode.toString());
    }
  }

  void deleteRace(String id) async {
    final response =
        await http.delete(Uri.http('localhost:8084', '/race/' + id));
    if (response.statusCode == 200) {}
  }

  Future<RaceDto> getRace(String id) async {
    var response = await http.get(Uri.http('localhost:8084', '/race/' + id));

    // Connection Ok
    var responseJson = json.decode(response.body);
    // responseJson.map((m) => races.add(RaceDto.fromJson(m))).toList();
    var res = RaceDto.fromJson(responseJson);
    races = [];
    races.add(res);
    print(races);
    return res;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Container(
        // color: const Color(0xffa9ee00),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Container(
              // A fixed-height child.
              // color: const Color(0xffeeee00),
              // Yellow
              height: 280.0,
              width: 500,
              alignment: Alignment.bottomLeft,
              child: Column(
                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Column(
                      children: [
                        TextField(
                          controller: _id,
                          decoration: InputDecoration(
                            labelText: 'id',
                            border: OutlineInputBorder(),
                          ),
                          maxLines: null,
                        ),
                        TextField(
                          controller: _name,
                          decoration: InputDecoration(
                              labelText: 'Name', border: OutlineInputBorder()),
                        ),
                        TextField(
                          controller: _engine,
                          decoration: InputDecoration(
                            labelText: 'engineCapacity',
                            border: OutlineInputBorder(),
                          ),
                          maxLines: null,
                        ),
                        ElevatedButton(
                          onPressed: () {
                            RaceDto expr;
                            var result =
                                save(_id.text, _name.text, _engine.text);
                            setState(() {
                                 fetchData();
                            });
                          },
                          child: Text('Submit'),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            deleteRace(_id.text);
                            setState(() {
                              fetchData();
                            });
                          },
                          child: Text('Delete'),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            setState(() {
                              var race = getRace(_id.text);
                            });
                          },
                          child: Text('GetById'),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            setState(() {
                              fetchData();
                            });
                          },
                          child: Text('GetAll'),
                        ),
                      ],
                    ),
                  ]),
            ),
            Container(
                // Another fixed-height child.
                height: 120.0,
                alignment: Alignment.center,
                child: ListView.builder(
                  itemCount: races.length,
                  itemBuilder: (BuildContext context, int index) {
                    var displRace = races[index];
                    return Text(displRace.toString());
                  },
                )),
          ],
        ),
      ),
    );
  }
}

List<RaceDto> racesFromJson(String str) =>
    List<RaceDto>.from(json.decode(str).map((x) => RaceDto.fromJson(x)));

String racesToJson(List<RaceDto> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class RaceDto {
  RaceDto({
    this.id,
    this.name,
    this.engineCapacity,
  });

  int? id;
  String? name;
  String? engineCapacity;

  factory RaceDto.fromJson(Map<String, dynamic> json) => RaceDto(
        id: json["id"],
        name: json["name"],
        engineCapacity: json["engineCapacity"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "engineCapacity": engineCapacity,
      };

  @override
  String toString() {
    return "id:" +
        id.toString() +
        ", name: " +
        name.toString() +
        ", engineCapacity: " +
        engineCapacity.toString();
  }
}
