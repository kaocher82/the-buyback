import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from "@angular/router";
declare let ga: Function;
import {Chart} from 'chart.js';
import { Http, Response } from '@angular/http';

@Component({
               selector: 'jhi-item-stock', templateUrl: './item-stock.component.html', styles: []
           })
export class ItemStockComponent implements OnInit, AfterViewInit {

    loading = true;
    data: any;
    private typeId: number;

    constructor(public router: Router, private route: ActivatedRoute, private http: Http,
                private location: Location) {
    }

    backClicked() {
        this.location.back();
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            this.typeId = params['typeId'];
        });
    }

    ngAfterViewInit() {
        this.load(this.typeId);
    }

    private load(typeId: number) {
        this.http.get('doctrines/stock/item-stock/BRAVE_FORT/' + typeId).map((res: Response) => {
            return res.json();
        }).subscribe((data) => {
            this.data = data;
            this.loading = false;
            this.setUpStockChart(data);
            this.setUpPriceChart(data);
        });
    }

    private setUpStockChart(data: any) {
        const fortStockHistory = data.fortStockHistory;
        const stockChartCanvas = document.getElementById("stockChart");
        const myChart = new Chart(stockChartCanvas, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Min # in Fort',
                    data: this.prepareData(fortStockHistory, "lowestQuantity", false),
                    // backgroundColor: ['rgba(0, 0, 0, 0.05)'],
                    borderColor: ['rgba(122, 122, 122, 0.8)'],
                    borderWidth: 1,
                    fill: false

                }, {
                    label: 'Max # in Fort',
                    data: this.prepareData(fortStockHistory, "highestQuantity", false),
                    backgroundColor: ['rgba(0, 0, 0, 0.05)'],
                    borderColor: ['rgba(122, 122, 122, 0.8)'],
                    borderWidth: 1,
                    fill: "-1"
                }, {
                    label: 'Target Stock',
                    data: this.prepareDataTargetStock(fortStockHistory, data.targetStock, false),
                    backgroundColor: ['rgba(100, 0, 0, 0.05)'],
                    borderColor: ['rgba(122, 30, 30, 0.8)'],
                    borderWidth: 1,
                    // fill: "-1"
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        stacked: false,
                    }], xAxes: [{
                        type: 'time',
                        time: {
                            unit: 'day'
                        }
                    }]
                },
                elements: {
                    line: {
                        tension: 0
                    }
                },
                responsive: true,
                maintainAspectRatio: true
            }
        });
    }

    private setUpPriceChart(data: any) {
        const fortStockHistory = data.fortStockHistory;
        const stockChartCanvas = document.getElementById("priceChart");
        const myChart = new Chart(stockChartCanvas, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Min sell price in Fort in mil ISK',
                    data: this.prepareData(fortStockHistory, "lowestMinSell", true),
                    // backgroundColor: ['rgba(0, 0, 0, 0.05)'],
                    borderColor: ['rgba(122, 122, 122, 0.8)'],
                    borderWidth: 1,
                    fill: false

                }, {
                    label: 'Max sell price in Fort in mil ISK',
                    data: this.prepareData(fortStockHistory, "highestMinSell", true),
                    backgroundColor: ['rgba(0, 0, 0, 0.05)'],
                    borderColor: ['rgba(122, 122, 122, 0.8)'],
                    borderWidth: 1,
                    fill: "-1"

                }, {
                    label: 'Overpriced Border',
                    data: this.prepareDataTargetStock(fortStockHistory, data.priceBorder, true),
                    backgroundColor: ['rgba(200, 200, 0, 0.05)'],
                    borderColor: ['rgba(200, 200, 0, 0.8)'],
                    borderWidth: 1,
                    // fill: "-1"
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        stacked: false,
                    }], xAxes: [{
                        type: 'time',
                        time: {
                            unit: 'day'
                        }
                    }]
                },
                elements: {
                    line: {
                        tension: 0
                    }
                },
                responsive: true,
                maintainAspectRatio: true
            }
        });
    }

    prepareData(data: any[], quantity: string, divByMil: boolean) {
        // data.sort(function(a, b) {
        //     return +new Date(a['date']).getTime() - +new Date(b['date']).getTime();
        // });
        const result = [];
        for (let i = 0; i < data.length; i++) {
            const el = data[i];
            let yAxis = el[quantity];
            if (divByMil) {
                yAxis = yAxis / 1000000;
            }
            result.push({x: el['date'], y: yAxis});
        }
        return result;
    }

    prepareDataTargetStock(data: any[], value: number, divByMil: boolean) {
        // data.sort(function(a, b) {
        //     return +new Date(a['date']).getTime() - +new Date(b['date']).getTime();
        // });
        if (divByMil) {
            value = value / 1000000;
        }
        const result = [];
        for (let i = 0; i < data.length; i++) {
            const el = data[i];
            result.push({x: el['date'], y: value});
        }
        return result;
    }
}
